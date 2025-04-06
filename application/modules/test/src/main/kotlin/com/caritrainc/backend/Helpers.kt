package com.caritrainc.backend

import com.caritrainc.backend.controller.ControllerExceptionHandler
import com.caritrainc.backend.exception.AppCustomException
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpSession
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.accept.ContentNegotiationManager
import org.springframework.web.context.support.StaticWebApplicationContext
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import org.springframework.web.util.NestedServletException

class ControllerTester<T : Any>(
    private val controller: T,
) {
    var userId: String? = null
    var roles: List<String> = emptyList()
    var jwtToken: String? = null
    var shouldLogIn: Boolean = false
    var session: HttpSession = MockHttpSession()
        private set

    /**
     * The result of the tests. If called before a 'perform' action is called, will throw an exception.
     */
    val result: ResultActionsDsl get() = lastResult!!

    var mvcResult: MvcResult? = null
        private set

    var mvcResultResponse: MockHttpServletResponse? = null
        private set

    private var lastResult: ResultActionsDsl? = null

    fun logIn(userId: String, roles: List<String> = listOf()) {
        this.userId = userId
        this.roles = roles
        shouldLogIn = true
    }

    fun logInWithToken(token: String) {
        jwtToken = token
        shouldLogIn = true
    }

    val mvc: MockMvc by lazy {
        MockMvcBuilders.standaloneSetup(controller).apply {
            // We set up a temporary application context and register the ControllerExceptionHandler controller advice.
            // Then, we install it as the exception resolver for the MockMVC.
            val applicationContext = StaticWebApplicationContext()
            applicationContext.registerSingleton("exceptionHandler", ControllerExceptionHandler::class.java)
            val webMvcConfigurationSupport = WebMvcConfigurationSupport()
            webMvcConfigurationSupport.applicationContext = applicationContext
            setHandlerExceptionResolvers(webMvcConfigurationSupport.handlerExceptionResolver(ContentNegotiationManager()))

//            addInterceptors(
//                SessionAuthenticationHandlerMapping()
//            )
        }.build()
    }

    /**
     * Perform a request. This will ALWAYS print output for debugging purposes.
     */
    private fun prePerform(request: MockHttpServletRequestDsl) {
        request.contentType = MediaType.APPLICATION_JSON
        jwtToken?.let {
            request.header(HttpHeaders.AUTHORIZATION, "Bearer $it")
        }
        if (shouldLogIn) {
            request.cookie(Cookie("t", "t").apply {
                domain = "/"
                maxAge = 3600
                path = "/"
                isHttpOnly = true
                secure = true
            })
        }
    }

    private fun postPerform(resultActionsDsl: ResultActionsDsl): ResultActionsDsl {
        lastResult = resultActionsDsl
        resultActionsDsl.andReturn().let {
            mvcResult = it
            mvcResultResponse = it.response
        }
        return result
    }

    /**
     * Perform a GET request with the given path and path parameters
     */
    fun performGet(path: String, vararg params: Any, block: MockHttpServletRequestDsl.() -> Unit) {
        try {
            postPerform(mvc.get(path, *params) {
                prePerform(this)
                contentType = MediaType.APPLICATION_JSON
                block()
            })
        } catch (e: NestedServletException) {
            throw e.rootCause
        }
    }

    /**
     * Perform a POST request with the given path and path parameters
     */
    fun performPost(path: String, vararg params: Any, block: MockHttpServletRequestDsl.() -> Unit) {
        try {
            postPerform(mvc.post(path, *params) {
                prePerform(this)
                contentType = MediaType.APPLICATION_JSON
                block()
            })
        } catch (e: NestedServletException) {
            throw e.rootCause
        }
    }

    /**
     * Perform a PUT request with the given path and path parameters
     */
    fun performPut(
        path: String,
        vararg params: Any,
        block: MockHttpServletRequestDsl.() -> Unit
    ) {
        try {
            postPerform(mvc.put(path, *params) {
                prePerform(this)
                contentType = MediaType.APPLICATION_JSON
                block()
            })
        } catch (e: NestedServletException) {
            throw e.rootCause
        }
    }

    /**
     * Perform a Delete request with the given path and path parameters
     */
    fun performDelete(
        path: String,
        vararg params: Any,
        block: MockHttpServletRequestDsl.() -> Unit
    ) {
        try {
            postPerform(mvc.delete(path, *params) {
                prePerform(this)
                contentType = MediaType.APPLICATION_JSON
                block()
            })
        } catch (e: NestedServletException) {
            throw e.rootCause
        }
    }

    inner class Results {
        /*
        * Prints the response of the result
        * */
        fun print() {
            result.andDo { print() }
        }


        /**
         * Ensures that a redirect with the given URL occurs.
         */
        fun redirect(url: String) {
            result.andExpect {
                redirectedUrl(url)
            }
        }

        /**
         * Ensure that the method resulted in the given Http status
         */
        fun status(status: org.springframework.http.HttpStatus) {
            status(status.value())
        }

        /**
         * Ensure that the method resulted in the given Http status
         */
        fun status(status: Int) {
            result.andExpect {
                status {
                    isEqualTo(status)
                }
            }
        }

        /**
         * Ensures that the session contains a value.
         * @param key the key to look up.
         * @param value the value. If null, any value is matched. Otherwise, only the given value will pass.
         */
        fun sessionContains(key: String, value: Any? = null) {
            result.andExpect {
                val sessionValue = session.getAttribute(key)!!

                if (value != null) {
                    assert(value == sessionValue)
                }
            }
        }

        /**
         * Make sure that the session does not contain a certain value
         */
        fun sessionDoesNotContain(key: String) {
            result.andExpect {
                assert(session.getAttribute(key) == null)
            }
        }

        fun cookieValue(cookieName: String): Any? {
            return mvcResultResponse!!.getCookie(cookieName)?.value
        }

        fun view(viewClass: Any) {
            result.andExpect {
                assert("${mvcResult!!.modelAndView!!.view!!.javaClass}" == "$viewClass")
            }
        }

        fun view(viewName: String) {
            result.andExpect {
                model {
                    view {
                        name(viewName)
                    }
                }
            }
        }

        /**
         * Ensures that the model contains a value.
         * @param key the key to look up.
         * @param value the value. If null, any value is matched. Otherwise, only the given value will pass.
         */
        fun modelContains(key: String, value: Any? = null) {
            result.andExpect {
                model {
                    attributeExists(key)
                    attribute(key, value)
                }
            }
        }

        /**
         * Make sure that the model does not contain a certain value
         */
        fun modelDoesNotContain(key: String) {
            result.andExpect {
                model {
                    attributeDoesNotExist(key)
                }
            }
        }

        private fun shouldBeJson() {
            result.andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
        }

        fun verifyJson(block: JsonResultValidator.() -> Unit) {
            shouldBeJson()
            JsonResultValidator().block()
        }

        fun verifyBadRequest(block: JsonResultValidator.() -> Unit) {
            JsonResultValidator().block()
        }

        fun verifyAppException(exception: AppCustomException) {
            verifyJson {
                validate("$.errorMessage", Matchers.`is`(exception.errorMessage))
                validate("$.errorCode", Matchers.`is`(exception.errorCode))
            }
        }
    }

    inner class JsonResultValidator {
        fun <T> validate(expression: String, matcher: Matcher<T>) {
            result.andExpect {
                jsonPath(expression, matcher)
            }
        }
    }

    /**
     * Call this after completing your action to check on the results. The Results class contains
     * a number of useful checks, but it is also reasonable to include your own assertions within the
     * block. While the 'expect' would not be totally necessary there, it's a nice documentation tool.
     */
    fun expect(block: Results.() -> Unit) {
        if (lastResult == null) {
            throw IllegalStateException("You cannot expect something unless you call one of the perform methods.")
        }
        Results().block()
    }
}

/**
 * Test a controller. Provide a block where you can configure the test harness and then
 * perform HTTP operations on the controller
 */
fun <T : Any> testController(
    controller: T,
    block: ControllerTester<T>.() -> Unit
) {
    ControllerTester(controller).apply(block)
}
