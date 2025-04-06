package com.caritrainc.backend.service.user

import org.flowable.engine.RuntimeService
import org.springframework.stereotype.Service

@Service
class RuleService(private val runtimeService: RuntimeService) {

    fun startProcess() {
        val processInstance = runtimeService.startProcessInstanceByKey("helloWorldProcess")
        println("Started process instance: ${processInstance.id}")
    }
    fun startUserEvaluation(name: String, age: Int,membershipType:String, status: String, discount: Double): Map<String, Any> {
        println("CHETNA: Starting user evaluation process for Name: $name, Age: $age, Status: $status, Discount: $discount")

        val processInstance = runtimeService.startProcessInstanceByKey(
            "userRuleEvaluationProcess",
            mapOf("name" to name, "age" to age, "status" to status, "discount" to discount)
        )

        println("CHETNA: Process started with ID: ${processInstance.id}, Variables: ${processInstance.processVariables}")
        return processInstance.processVariables
    }
}
