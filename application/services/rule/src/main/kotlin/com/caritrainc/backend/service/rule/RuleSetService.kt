package com.caritrainc.backend.service.rule

import com.caritrainc.backend.database.collection.app.RuleSet
import com.caritrainc.backend.model.NewRuleRequest
import com.caritrainc.backend.database.collection.app.Rule
import com.caritrainc.backend.database.repository.app.BusinessRepository
import com.caritrainc.backend.database.repository.app.RuleRepository
import com.caritrainc.backend.database.repository.app.RuleSetRepository
import com.caritrainc.backend.model.NewRuleSetRequest
import com.caritrainc.backend.model.UpdateRuleRequest
import com.caritrainc.backend.model.UpdateRuleSetRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class RulesSetService {

    @Autowired
    lateinit var ruleSetRepository: RuleSetRepository

    @Autowired
    lateinit var ruleRepository: RuleRepository

    @Autowired
    lateinit var businessRepository: BusinessRepository


    @Transactional
    fun getRuleSetByName(name: String): RuleSet {
        return ruleSetRepository.findByName(name)
    }

    @Transactional
    fun getRuleSetByName(id:UUID,name: String): RuleSet {
        return ruleSetRepository.findByIdAndName(id,name)
    }


    @Transactional
    fun createRuleSet(newRuleSetRequest: NewRuleSetRequest,businessId:UUID): NewRuleSetRequest {
        val business = businessRepository.findById(businessId)
            .orElseThrow { IllegalArgumentException("Business with ID $businessId not found") }
        val ruleSet = RuleSet(
            name = newRuleSetRequest.name,
            condition = newRuleSetRequest.condition,
            factModelName = newRuleSetRequest.factModelName,
            business = business
        )


        val savedRuleSet = ruleSetRepository.save(ruleSet)

        val rules = newRuleSetRequest.rules.map { NewRuleRequest ->
            Rule(
                field = NewRuleRequest.field,
                operator = NewRuleRequest.operator,
                value = NewRuleRequest.value,
                action = NewRuleRequest.action,
                actionValue = NewRuleRequest.actionValue,
                ruleSet = savedRuleSet
            )
        }

        // Save Rules to the database
        ruleRepository.saveAll(rules)

        // Return the same NewRuleSetRequest object (or you could return an entity if necessary)
        return newRuleSetRequest
    }

    fun getRuleSetWithRules(name: String, factModelName: String): UpdateRuleSetRequest {
        val ruleSet = ruleSetRepository.findByNameAndFactModelNameWithRules(name, factModelName)
        print("rulesSet $ruleSet")
        val businessId = ruleSet.business.id

        val ruleSetModelTest = UpdateRuleSetRequest(
            name = ruleSet.name,
            condition = ruleSet.condition,
            factModelName = ruleSet.factModelName,
            businessId = businessId,
            rules = ruleSet.rules.map { rule ->
                UpdateRuleRequest(
                    field = rule.field,
                    operator = rule.operator,
                    value = rule.value,
                    action = rule.action,
                    actionValue = rule.actionValue,
                    ruleSetId = rule.id ?: throw IllegalArgumentException("Rule ID cannot be null")
                )
            }
        ).toString()
        print("ruleSetModelTest, $ruleSetModelTest ")
        return UpdateRuleSetRequest(
            name = ruleSet.name,
            condition = ruleSet.condition,
            factModelName = ruleSet.factModelName,
            businessId = businessId ,
            rules = ruleSet.rules.map { rule ->
                UpdateRuleRequest(
                    field = rule.field,
                    operator = rule.operator,
                    value = rule.value,
                    action = rule.action,
                    actionValue = rule.actionValue,
                    ruleSetId = rule.id ?: throw IllegalArgumentException("Rule ID cannot be null")
                )
            }
        )
    }



}
