package com.caritrainc.backend.database.model

data class DashboardPreferences(
    val calendar: CalendarPreferences = CalendarPreferences()
)

data class CalendarPreferences(
    val timeZone: String = "local",
    val timeZoneName: String = "Local (System Time)",
    val slotRange: String = "All Day",
    val slotMinTime: String = "00:00:00",
    val slotMaxTime: String = "24:00:00",
    val slotDuration: String = "00:30:00"
)