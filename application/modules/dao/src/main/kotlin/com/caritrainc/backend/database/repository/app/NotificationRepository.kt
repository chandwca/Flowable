package com.caritrainc.backend.database.repository.app

import com.caritrainc.backend.database.collection.app.Notifications
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NotificationRepository : JpaRepository<Notifications, UUID> {
}