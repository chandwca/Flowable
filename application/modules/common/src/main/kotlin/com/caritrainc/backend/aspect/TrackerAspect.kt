package com.caritrainc.backend.aspect

import com.caritrainc.backend.logging.Loggable
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*
import org.springframework.stereotype.Component


@Aspect
@Component
class TrackerAspect {
    // Define a pointcut for services and controllers (methods starting with "find", "save", or "get")
    @Pointcut("execution(* com.caritrainc.backend..*(..))")
    fun serviceAndControllerMethods() {
    }

    @Before("serviceAndControllerMethods()")
    fun logBefore(joinPoint: JoinPoint) {
        val methodName = joinPoint.signature.name
        val parameters = joinPoint.args.joinToString(", ") { it.toString() }
        log.info("Before executing method: $methodName with parameters: [$parameters]")
    }

    // Log the result after method execution
    @AfterReturning(pointcut = "serviceAndControllerMethods()", returning = "result")
    fun logAfterReturning(joinPoint: JoinPoint, result: Any?) {
        val methodName = joinPoint.signature.name
        log.info("Method $methodName executed successfully. Result: $result")
    }

    // Log exceptions after method execution
    @AfterThrowing(pointcut = "serviceAndControllerMethods()", throwing = "exception")
    fun logAfterThrowing(joinPoint: JoinPoint, exception: Throwable) {
        val methodName = joinPoint.signature.name
        log.error("Method $methodName threw exception: ${exception.message}", exception)
    }

    companion object : Loggable()
}