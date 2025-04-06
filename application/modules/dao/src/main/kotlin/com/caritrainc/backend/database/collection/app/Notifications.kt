package com.caritrainc.backend.database.collection.app

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "notifications", schema = "app")
data class Notifications(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null,

    @OneToOne
    @JoinColumn(name = "business_id", referencedColumnName = "id", nullable = false)
    val business: Business,

    @Column(columnDefinition = "TEXT")
    var notification: String,

    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()
)