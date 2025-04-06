package com.caritrainc.backend.controller

import com.caritrainc.backend.exception.AppException
import com.caritrainc.backend.jacksonObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(AppException::class)
    fun handleAppException(response: HttpServletResponse, e: AppException) {
        response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        response.writer.write(
            jacksonObjectMapper.writeValueAsString(
                mapOf(
                    "errorMessage" to e.exception.errorMessage,
                    "errorCode" to e.exception.errorCode
                )
            )
        )
        response.contentType = MediaType.APPLICATION_JSON_VALUE
    }
}