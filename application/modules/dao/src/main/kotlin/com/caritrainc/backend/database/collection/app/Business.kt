package com.caritrainc.backend.database.collection.app

import com.caritrainc.backend.database.converter.app.BusinessLimitsConverter
import com.caritrainc.backend.database.model.BusinessLimits
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "businesses", schema = "app")
data class Business(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @field:NotBlank
    @field:Size(min = 1, max = 140)
    @Column(nullable = false, length = 140)
    var name: String,

    @Column(length = 10000)
    var about: String? = null,

    @field:Email
    @field:NotBlank
    @Column(nullable = false, unique = true)
    var email: String,

    @field:NotBlank
    @Column(nullable = false)
    var phoneNumber: String,


    @CreationTimestamp
    @Column(updatable = false)
    var createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null
)