package com.caritrainc.backend.database.repository.app

import com.caritrainc.backend.database.collection.app.Business
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BusinessRepository : JpaRepository<Business, UUID> {
}