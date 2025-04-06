package com.caritrainc.backend.model

import jakarta.validation.constraints.*
import java.util.UUID

// For updating an existing RuleSet
open class UpdateRuleSetRequest(

    open val name: String,
    open val condition: String,
    open val factModelName: String,
    open val rules: List<UpdateRuleRequest> = emptyList(),
    open val businessId: UUID?
)

// For creating a new RuleSet
data class NewRuleSetRequest(
    override val name: String,
    override val condition: String,
    override val factModelName: String,
    override val rules: List<UpdateRuleRequest>,
    override val businessId: UUID?
) : UpdateRuleSetRequest(name, condition, factModelName, rules,businessId)
