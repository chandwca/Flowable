package com.caritrainc.backend.service.notification

import org.flowable.engine.delegate.DelegateExecution
import org.flowable.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component
class NotifyUserService : JavaDelegate {

    override fun execute(execution: DelegateExecution) {
        println("Executing NotifyUserService...")

        println("Notification: Hello  you have received a discount ")
        println("send an email")

    }
}
