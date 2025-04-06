package com.caritrainc.backend.database.collection.app

import com.caritrainc.backend.model.ImageType
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "defaultimages", schema = "app")
class DefaultImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null

    var categoryId: String? = null

    @Column(nullable = false)
    lateinit var imageUrl: String

    @Enumerated(EnumType.STRING)
    lateinit var type: ImageType

    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()

//    @Version
//    var version: Long = 1
}