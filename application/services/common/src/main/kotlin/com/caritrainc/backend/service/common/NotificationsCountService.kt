package com.caritrainc.backend.service.common

import com.caritrainc.backend.database.collection.app.Business
import com.caritrainc.backend.database.collection.app.Notifications
import com.caritrainc.backend.database.collection.app.NotificationsCount
import com.caritrainc.backend.database.repository.app.NotificationRepository
import com.caritrainc.backend.database.repository.app.NotificationsCountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NotificationsCountService {

    @Autowired
    lateinit var notificationsCountRepository: NotificationsCountRepository

    fun addNotificationCount(business: Business) {
        val notification = NotificationsCount(business = business)
        notificationsCountRepository.save(notification)
    }

    fun updateNotificationCount(business: Business) {
        val notifications = notificationsCountRepository.getByBusiness(business)
        notifications.unreadCount++
        notificationsCountRepository.save(notifications)
    }

}