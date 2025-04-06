package com.caritrainc.backend.controller

import com.caritrainc.backend.database.collection.app.RuleSet
import com.caritrainc.backend.model.NewRuleSetRequest
import com.caritrainc.backend.service.rule.RulesSetService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/rulesets")
class RuleSetRestController {

    @Autowired
    lateinit var rulesSetService: RulesSetService


    @GetMapping("/{name}")
    fun getRuleSetByName(@PathVariable name: String): ResponseEntity<RuleSet> {
        val ruleSet = rulesSetService.getRuleSetByName(name)
        return ResponseEntity.ok(ruleSet)
    }

    // Endpoint to create a new RuleSet
    @PostMapping("/{businessID}")
    fun createRuleSet(@RequestBody ruleSet: NewRuleSetRequest,@PathVariable businessID: UUID): ResponseEntity<NewRuleSetRequest> {

        val createdRuleSet = rulesSetService.createRuleSet(ruleSet,businessID)

        return ResponseEntity.status(HttpStatus.CREATED).body(createdRuleSet)
    }
}
