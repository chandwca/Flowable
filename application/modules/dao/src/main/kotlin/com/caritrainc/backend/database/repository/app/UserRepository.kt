package com.caritrainc.backend.database.repository.app

import com.caritrainc.backend.database.collection.app.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun existsByName(name: String): Boolean
    fun findByName(name: String): User?
}