package com.caritrainc.backend.database.collection.app

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "notifications_count", schema = "app")
data class NotificationsCount(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null,

    @OneToOne
    @JoinColumn(name = "business_id", referencedColumnName = "id", nullable = false)
    val business: Business,

    var unreadCount: Int = 0,

    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()
)