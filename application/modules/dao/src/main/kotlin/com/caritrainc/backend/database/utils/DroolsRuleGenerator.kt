package com.caritrainc.backend.database.utils

import com.caritrainc.backend.model.NewRuleSetRequest
import com.caritrainc.backend.model.UpdateRuleSetRequest
import java.util.List
import java.util.stream.Collectors

object DroolsRuleGenerator {

    fun generateCompositeDroolsRule(ruleSet: UpdateRuleSetRequest): String {
        // Create a list of individual conditions from the rules
        val conditions = ruleSet.rules.map { rule ->
            when (rule.operator.uppercase()) {
                "GREATER_THAN" -> "${rule.field} > ${formatValue(rule.value)}"
                "LESS_THAN" -> "${rule.field} < ${formatValue(rule.value)}"
                "EQUAL_TO" -> "${rule.field} == ${formatValue(rule.value)}"
//                "IN" -> "${rule.field} in (${(rule.value as List<*>).joinToString(", ") { formatValue(it) }})"
                "BETWEEN" -> {
                    val (min, max) = rule.value as Pair<*, *>
                    "${rule.field} >= ${formatValue(min)} && ${rule.field} <= ${formatValue(max)}"
                }
                else -> throw IllegalArgumentException("Unsupported operator: ${rule.operator}")
            }
        }

        // Join conditions based on the overall condition (AND/OR)
        val compositeCondition = when (ruleSet.condition.uppercase()) {
            "AND" -> conditions.joinToString(" && ")
            "OR" -> conditions.joinToString(" || ")
            else -> throw IllegalArgumentException("Unsupported condition: ${ruleSet.condition}")
        }
        val fact = ruleSet.factModelName

        print("fact model name")
        print("ruleset rule action, ${ruleSet.rules}")

        val actions = ruleSet.rules.mapNotNull { rule ->
            rule.actionValue?.let { "modify($fact) { set${rule.action.capitalize()}($it) };" }
        }.joinToString("\n")

        // Build the Drools DRL rule using the dynamic fact model name and composite condition.
        return """
            import com.caritrainc.backend.database.collection.app.${ruleSet.factModelName};

            rule "${ruleSet.name}"
            when
                $fact : ${ruleSet.factModelName}($compositeCondition)
            then
                $actions
                System.out.println("Composite condition '${ruleSet.name}' met for: " + $fact);
            end
        """.trimIndent()
    }

    // Helper function to format values according to their type
    private fun formatValue(value: Any?): String {
        return when (value) {
            is String -> "\"$value\""
            is Boolean -> value.toString()
            is Number -> value.toString()
//            is List<*> -> value.joinToString(", ") { formatValue(it) }
            is Pair<*, *> -> "${formatValue(value.first)} && ${formatValue(value.second)}"
            else -> throw IllegalArgumentException("Unsupported value type: ${value?.javaClass?.name}")
        }
    }
}
