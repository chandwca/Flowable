package com.caritrainc.backend.database.collection.app

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "users", schema = "app")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null

    var name: String = ""

    var age: Int = 0

    var membershipType:String=""

    var status: String=""

    var discount: Double = 0.0


    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()

//    @Version
//    var version: Long = 1
}