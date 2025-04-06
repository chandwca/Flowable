package com.caritrainc.backend.database.repository.app

import com.caritrainc.backend.database.collection.app.Staff
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StaffRepository : JpaRepository<Staff, UUID> {
}