package com.caritrainc.backend.model

import jakarta.validation.constraints.*
import java.util.*

open class UpdateRuleRequest(
    open val field: String,
    open val operator: String,  // e.g., "greater than", "equals"
    open val value: String,  // e.g., "60"
    open val action: String,  // e.g., "discount", "bonus", "coupon"
    open val actionValue: Double? = null,  // e.g., 20.0 for 20% discount
    open val ruleSetId: UUID? // Referencing RuleSet id
)

data class NewRuleRequest(
    override val field: String,
    override val operator: String,
    override val value: String,
    override val action: String,
    override val actionValue: Double? = null,
    override val ruleSetId: UUID?
) : UpdateRuleRequest(field, operator, value, action, actionValue, ruleSetId)