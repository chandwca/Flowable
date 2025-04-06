package com.caritrainc.backend.controller.user

import com.caritrainc.backend.CaritraApplicationTests
import com.caritrainc.backend.database.repository.app.UserRepository
import com.caritrainc.backend.exception.AppCustomException
import com.caritrainc.backend.jacksonObjectMapper
import com.caritrainc.backend.model.NewUserRequest
import com.caritrainc.backend.model.UpdateUserRequest
import com.caritrainc.backend.testController
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class UserRestControllerTest @Autowired constructor(
    private val userRestController: UserRestController,
    private val userRepository: UserRepository
) : CaritraApplicationTests() {

    @Test
    fun testGetAllUser() {
        testController(userRestController) {
            performGet(USER_REST_CONTROLLER_ACTION) {}
            expect {
                print()
                status(HttpStatus.OK)
                verifyJson {
                    validate("$.content", Matchers.hasSize<Int>(1))
                    validate("$.content[0].id", Matchers.`is`(TEST_USER_ID))
                    validate("$.content[0].name", Matchers.`is`("Test"))
                    validate("$.content[0].createdAt", Matchers.hasSize<Int>(7))
                    validate("$.content[0].updatedAt", Matchers.hasSize<Int>(7))

                    validate("$.pageable.pageNumber", Matchers.`is`(0))
                    validate("$.pageable.pageSize", Matchers.`is`(10))
                    validate("$.pageable.sort.empty", Matchers.`is`(true))
                    validate("$.pageable.sort.unsorted", Matchers.`is`(true))
                    validate("$.pageable.sort.sorted", Matchers.`is`(false))
                    validate("$.pageable.offset", Matchers.`is`(0))
                    validate("$.pageable.unpaged", Matchers.`is`(false))
                    validate("$.pageable.paged", Matchers.`is`(true))

                    validate("$.last", Matchers.`is`(true))
                    validate("$.totalElements", Matchers.`is`(1))
                    validate("$.totalPages", Matchers.`is`(1))
                    validate("$.first", Matchers.`is`(true))
                    validate("$.size", Matchers.`is`(10))
                    validate("$.number", Matchers.`is`(0))
                    validate("$.sort.empty", Matchers.`is`(true))
                    validate("$.sort.unsorted", Matchers.`is`(true))
                    validate("$.sort.sorted", Matchers.`is`(false))
                    validate("$.numberOfElements", Matchers.`is`(1))
                    validate("$.empty", Matchers.`is`(false))
                }
            }
        }
    }

    @Test
    fun testGetAllUser_MockEmptyUser() {
        userRepository.deleteAll()
        testController(userRestController) {
            performGet(USER_REST_CONTROLLER_ACTION) {}
            expect {
                print()
                status(HttpStatus.OK)
                verifyJson {
                    validate("$.content", Matchers.hasSize<Int>(0))

                    validate("$.pageable.pageNumber", Matchers.`is`(0))
                    validate("$.pageable.pageSize", Matchers.`is`(10))
                    validate("$.pageable.sort.empty", Matchers.`is`(true))
                    validate("$.pageable.sort.unsorted", Matchers.`is`(true))
                    validate("$.pageable.sort.sorted", Matchers.`is`(false))
                    validate("$.pageable.offset", Matchers.`is`(0))
                    validate("$.pageable.unpaged", Matchers.`is`(false))
                    validate("$.pageable.paged", Matchers.`is`(true))

                    validate("$.last", Matchers.`is`(true))
                    validate("$.totalElements", Matchers.`is`(0))
                    validate("$.totalPages", Matchers.`is`(0))
                    validate("$.first", Matchers.`is`(true))
                    validate("$.size", Matchers.`is`(10))
                    validate("$.number", Matchers.`is`(0))
                    validate("$.sort.empty", Matchers.`is`(true))
                    validate("$.sort.unsorted", Matchers.`is`(true))
                    validate("$.sort.sorted", Matchers.`is`(false))
                    validate("$.numberOfElements", Matchers.`is`(0))
                    validate("$.empty", Matchers.`is`(true))
                }
            }
        }
    }

    @Test
    fun testPostUser() {
        testController(userRestController) {
            performPost(USER_REST_CONTROLLER_ACTION) {
                content = jacksonObjectMapper.writeValueAsString(NewUserRequest(name = "New User"))
            }
            expect {
                print()
                status(HttpStatus.CREATED)
                verifyJson {
                    validate("$.id", Matchers.matchesPattern(UUID_REG_EXP))
                    validate("$.name", Matchers.`is`("New User"))
                    validate("$.createdAt", Matchers.hasSize<Int>(7))
                    validate("$.updatedAt", Matchers.hasSize<Int>(7))
                }
            }
        }
    }

    @Test
    fun testPostUser_ValidateNameSize() {
        testController(userRestController) {
            performPost(USER_REST_CONTROLLER_ACTION) {
                content = jacksonObjectMapper.writeValueAsString(NewUserRequest(name = "New"))
            }
            expect {
                print()
                status(HttpStatus.BAD_REQUEST)
                verifyBadRequest {
                    validate("$.timestamp", Matchers.matchesPattern(TIMESTAMP_REG_EXP))
                    validate("$.status", Matchers.`is`(400))
                    validate("$.error", Matchers.`is`("Bad Request"))
                    validate("$.path", Matchers.`is`("/api/v1/users"))
                }
            }
        }
    }

    @Test
    fun testUpdateUser() {
        testController(userRestController) {
            performPut(USER_REST_CONTROLLER_ACTION_WITH_USER_ID, TEST_USER_ID) {
                content = jacksonObjectMapper.writeValueAsString(UpdateUserRequest(name = "Updated User"))
            }
            expect {
                print()
                status(HttpStatus.OK)
                verifyJson { }
            }
        }
    }

    @Test
    fun testUpdateUser_ValidateNameSize() {
        testController(userRestController) {
            performPut(USER_REST_CONTROLLER_ACTION_WITH_USER_ID, TEST_USER_ID) {
                content = jacksonObjectMapper.writeValueAsString(UpdateUserRequest(name = "User"))
            }
            expect {
                print()
                status(HttpStatus.BAD_REQUEST)
                verifyBadRequest {
                    validate("$.timestamp", Matchers.matchesPattern(TIMESTAMP_REG_EXP))
                    validate("$.status", Matchers.`is`(400))
                    validate("$.error", Matchers.`is`("Bad Request"))
                    validate("$.path", Matchers.`is`("/api/v1/users"))
                }
            }
        }
    }

    @Test
    fun testUpdateUser_mockInvalidUser() {
        testController(userRestController) {
            performPut(USER_REST_CONTROLLER_ACTION_WITH_USER_ID, TEST_INVALID_USER_ID) {
                content = jacksonObjectMapper.writeValueAsString(UpdateUserRequest(name = "Updated User"))
            }
            expect {
                print()
                status(HttpStatus.INTERNAL_SERVER_ERROR)
                verifyAppException(AppCustomException.UserNotFoundException)
            }
        }
    }

    @Test
    fun testDeleteUser() {
        testController(userRestController) {
            performDelete(USER_REST_CONTROLLER_ACTION_WITH_USER_ID, TEST_USER_ID) {}
            expect {
                print()
                status(HttpStatus.NO_CONTENT)
                verifyJson { }
            }
        }
    }

    companion object {
        private const val USER_REST_CONTROLLER_ACTION = "/api/v1/users"
        private const val USER_REST_CONTROLLER_ACTION_WITH_USER_ID = "/api/v1/users/{userId}"
    }
}