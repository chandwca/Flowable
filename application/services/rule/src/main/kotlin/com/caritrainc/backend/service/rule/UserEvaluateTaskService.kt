package com.caritrainc.backend.service.rule

import com.caritrainc.backend.database.collection.app.User
import com.caritrainc.backend.model.UpdateUserRequest
import com.caritrainc.backend.service.common.DroolRuleService
import org.flowable.engine.delegate.DelegateExecution
import org.flowable.engine.delegate.JavaDelegate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.pulsar.function.PulsarFunctionOperations.logger
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
@Component
class UserEvaluateTaskService(private val rulesSetService: RulesSetService,
                              private val droolRuleService: DroolRuleService):JavaDelegate {

    override fun execute(execution: DelegateExecution) {
        println("Hello, Flowable with Kotlin!")
        print("at component works, $execution")

//        rules name will be sent from the front end and the Context as well
        val rules = rulesSetService.getRuleSetWithRules("Discount Rules", "User")


//        we are evaluating user so we are getting the details of the user
        val userRequest = User().apply {
            name = execution.getVariable("name") as? String ?: "Unknown"
            age = execution.getVariable("age") as? Int ?: 0
            membershipType = execution.getVariable("membershipType") as? String ?: "Basic"
            status = execution.getVariable("status") as? String ?: "Inactive"
            discount = execution.getVariable("discount") as? Double ?: 0.0
        }

        println("Rules are extracted for flowable and drools integration $rules")
        droolRuleService.updateRules(rules)
        val evaluatedUser: User = droolRuleService.evaluateFact(userRequest)

        println("DEBUG: Evaluated User: $evaluatedUser")
        logger.info("Evaluated User: ${evaluatedUser.discount}")

        execution.setVariable("discount", evaluatedUser.discount)



    }
}