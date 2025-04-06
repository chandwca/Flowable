package com.caritrainc.backend.config

import org.kie.api.KieBase
import org.kie.api.runtime.KieSession
import org.kie.internal.utils.KieHelper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.beans.factory.annotation.Value

@Configuration
class DroolsConfig {

    @Bean
    fun kieHelper(): KieHelper {
        return KieHelper()
    }

    @Bean
    @Scope("singleton")
    fun kieBase(kieHelper: KieHelper): KieBase {
        // You can initialize the KieBase here if needed or dynamically at runtime using the DroolRuleService
        return kieHelper.build()
    }

    @Bean
    @Scope("singleton")
    fun kieSession(kieBase: KieBase): KieSession {
        return kieBase.newKieSession()
    }
}
