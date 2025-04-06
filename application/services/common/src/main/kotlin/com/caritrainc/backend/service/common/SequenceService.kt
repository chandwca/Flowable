package com.caritrainc.backend.service.common

import com.caritrainc.backend.database.collection.app.Sequence
import com.caritrainc.backend.database.repository.app.SequenceRepository
import com.caritrainc.backend.model.SequenceType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SequenceService {

    @Autowired
    lateinit var sequenceRepository: SequenceRepository

    private fun findByTypeOrCreate(type: SequenceType): Sequence {
        return sequenceRepository.findByType(type).orElseGet { sequenceRepository.save(Sequence(type = type)) }
    }

    fun getIncrementNo(type: SequenceType): Int {
        val sequence = findByTypeOrCreate(type)
        sequence.next++
        sequenceRepository.save(sequence)
        return sequence.next
    }

    fun getIncrementString(type: SequenceType): String {
        val sequence = findByTypeOrCreate(type)
        sequence.next++
        sequenceRepository.save(sequence)
        return "${type.type}${sequence.next}"
    }
}