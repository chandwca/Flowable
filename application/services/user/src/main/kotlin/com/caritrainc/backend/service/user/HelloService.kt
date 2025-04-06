package com.caritrainc.backend.service.user

import org.flowable.engine.delegate.DelegateExecution
import org.flowable.engine.delegate.JavaDelegate
import org.springframework.stereotype.Service

class HelloService:JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        println("Hello, Flowable with Kotlin!")

    }
}