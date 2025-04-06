package com.caritrainc.backend.service.common

import com.caritrainc.backend.database.collection.app.Business
import com.caritrainc.backend.database.collection.app.Notifications
import com.caritrainc.backend.database.repository.app.BusinessRepository
import com.caritrainc.backend.database.repository.app.NotificationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NotificationsService {

    @Autowired
    lateinit var notificationRepository: NotificationRepository

    @Autowired
    lateinit var notificationsCountService: NotificationsCountService

    fun addNotification(business: Business, notificationMessage: String) {
        val notification = Notifications(
            business = business,
            notification = notificationMessage
        )
        notificationRepository.save(notification)
        notificationsCountService.updateNotificationCount(business)
    }
}