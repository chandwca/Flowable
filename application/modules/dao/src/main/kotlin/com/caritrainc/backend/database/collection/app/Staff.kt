package com.caritrainc.backend.database.collection.app

import com.caritrainc.backend.database.converter.app.DashboardPreferencesConverter
import com.caritrainc.backend.database.model.DashboardPreferences
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "staffs", schema = "app")
data class Staff(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @OneToOne
    @JoinColumn(name = "business_id", referencedColumnName = "id", nullable = false)
    val business: Business,

    @Column(nullable = false)
    val userId: UUID,

    @Column(nullable = false)
    val firstName: String,

    @Column(nullable = false)
    val lastName: String,

    @Column(nullable = false)
    val isOwner: Boolean = false,

    @Column(nullable = false)
    val role: String,

    @Column(nullable = false)
    val isActive: Boolean = true,

    @Column
    val email: String? = null,

    @Column
    val phoneNumber: String? = null,

    @Column
    val image: String? = null,

    @Column(nullable = false)
    val isDefaultImage: Boolean = true,

    @Column(length = 10000)
    val profile: String? = null,

    @Column(nullable = false)
    val memberId: String,

    @Column(nullable = false)
    val profileAccess: Boolean = false,

    @Column(nullable = false)
    val schedulingAccess: Boolean = false,

    @Column(nullable = false)
    val dashboardAccess: Boolean = true,

    @Column(nullable = false)
    val financeAccess: Boolean = false,

    @Column(nullable = false)
    val reportAccess: Boolean = false,

    @Column(nullable = false)
    val checkInAccess: Boolean = false,

    @Column
    val createdBy: UUID? = null,

    @Column
    val updatedBy: UUID? = null,

    @Convert(converter = DashboardPreferencesConverter::class)
    @Column(nullable = false, columnDefinition = "TEXT")
    val dashboardPreferences: DashboardPreferences = DashboardPreferences(),

    @Column(updatable = false)
    val createdAt: java.time.LocalDateTime? = null,

    @Column
    val updatedAt: java.time.LocalDateTime? = null
)