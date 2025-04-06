package com.caritrainc.backend.database.collection.app

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "rules", schema = "app")
data class Rule(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @Column(nullable = false)
    val field: String,  // e.g., "age"

    @Column(nullable = false)
    val operator: String,  // e.g., "greater than", "equals"

    @Column(nullable = false)
    val value: String,  // e.g., "60" (used for comparison)

    @Column(nullable = false)
    val action: String,  // e.g., "discount", "bonus", "coupon"

    @Column(nullable = true)
    val actionValue: Double? = null,  // Value for the action, e.g., 20.0 for 20% discount


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruleset_id", nullable = false)
    @JsonBackReference
    val ruleSet: RuleSet
) {
    override fun toString(): String {
        return "Rule(id=$id, field='$field', operator='$operator', value='$value', action='$action', actionValue=$actionValue)"
    }
}
