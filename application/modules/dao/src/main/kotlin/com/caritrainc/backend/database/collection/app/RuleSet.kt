package com.caritrainc.backend.database.collection.app

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "rulesets", schema="app")
data class RuleSet(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @Column(nullable = false)
    val name: String,              // e.g., "Adult Verification"

    @Column(nullable = false)
    val condition: String,         // e.g., "AND" or "OR"

    @Column(nullable = false)
    val factModelName: String,     // e.g., "Person"

    @OneToMany(mappedBy = "ruleSet", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
//    @JsonManagedReference
    var rules: List<Rule> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    val business: Business
)
{
    override fun toString(): String {
        return "RuleSet(id=$id, name='$name', condition='$condition', factModelName='$factModelName')"
    }
}
