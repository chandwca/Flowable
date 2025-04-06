package com.caritrainc.backend.service.common

import com.caritrainc.backend.database.collection.app.User
import com.caritrainc.backend.database.utils.DroolsRuleGenerator
import com.caritrainc.backend.model.UpdateRuleSetRequest
import com.caritrainc.backend.model.UpdateUserRequest
import org.flowable.engine.delegate.DelegateExecution
import org.kie.api.KieBase
import org.kie.api.runtime.KieSession
import org.kie.internal.utils.KieHelper
import org.springframework.stereotype.Service
import java.util.concurrent.locks.ReentrantLock

@Service
class DroolRuleService {

    private val lock = ReentrantLock()
    private var kieSession: KieSession? = null
    private var kieBase: KieBase? = null

    fun updateRules(ruleSet: UpdateRuleSetRequest) {
        lock.lock()
        try {
            // Generate DRL dynamically based on RuleSetModel
            val drlContent = DroolsRuleGenerator.generateCompositeDroolsRule(ruleSet)
            println("Generated DRL: \n$drlContent")

            // Use KieHelper to compile DRL dynamically at runtime
            val kieHelper = KieHelper()
            kieHelper.addContent(drlContent, "dynamicRules.drl")

            // Build KieBase from KieHelper
            kieBase = kieHelper.build()

            // Create a new KieSession from KieBase
            kieSession = kieBase?.newKieSession()
        } finally {
            lock.unlock()
        }
    }


    fun evaluateFact(fact: User): User {
        val session = kieSession ?: throw IllegalStateException("No active Drools session. Update rules first.")
        println("DEBUG: Before inserting into Drools - User Discount: ${fact.discount}")
        session.insert(fact)
        session.fireAllRules()
        print("RULES WERE FIRED")
        println("DEBUG: After firing rules - User Discount: ${fact.discount}")
        session.dispose()
        return fact
    }

    fun getKieSession(): KieSession? {
        return kieSession
    }

    fun getKieBase(): KieBase? {
        return kieBase
    }
}
