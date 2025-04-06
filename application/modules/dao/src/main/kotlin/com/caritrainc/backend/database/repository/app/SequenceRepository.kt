package com.caritrainc.backend.database.repository.app

import com.caritrainc.backend.database.collection.app.Sequence
import com.caritrainc.backend.model.SequenceType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SequenceRepository : JpaRepository<Sequence, String> {

    fun findByType(type: SequenceType): Optional<Sequence>
}
