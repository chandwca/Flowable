package com.caritrainc.backend.database.repository.app

import com.caritrainc.backend.database.collection.app.RuleSet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RuleSetRepository : JpaRepository<RuleSet, UUID> {
    fun findByName(name: String): RuleSet
    fun findByIdAndName(id:UUID,name: String): RuleSet

    @Query("SELECT r FROM RuleSet r JOIN FETCH r.rules WHERE r.name = :name AND r.factModelName = :factModelName")
    fun findByNameAndFactModelNameWithRules(name: String, factModelName: String): RuleSet
}




