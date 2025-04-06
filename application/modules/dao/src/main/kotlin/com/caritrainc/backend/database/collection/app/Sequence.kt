package com.caritrainc.backend.database.collection.app

import com.caritrainc.backend.model.SequenceType
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "sequences", schema = "app")
data class Sequence(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: UUID? = null,
    val type: SequenceType,
    @Column(nullable = false) var next: Int = 0
)