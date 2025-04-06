package com.caritrainc.backend.filter

import jakarta.servlet.*
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.*

@Component
@WebFilter(urlPatterns = ["/*"])
class CorrelationIdFilter : Filter {
    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig?) {
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        // Retrieve correlation ID from the request header, or generate a new one
        var correlationId = httpRequest.getHeader(CORRELATION_ID_HEADER)
        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString() // Generate a new correlation ID if missing
        }

        // Store the correlation ID in MDC for logging
        MDC.put(CORRELATION_ID_LOG_KEY, correlationId)

        // Add the correlation ID to the response header for propagation
        httpResponse.setHeader(CORRELATION_ID_HEADER, correlationId)

        try {
            chain.doFilter(request, response) // Continue with the request processing
        } finally {
            MDC.remove(CORRELATION_ID_LOG_KEY) // Clean up the MDC after request is processed
        }
    }

    override fun destroy() {
    }

    companion object {
        const val CORRELATION_ID_HEADER: String = "X-Correlation-ID"
        private const val CORRELATION_ID_LOG_KEY = "correlationId"
    }
}
