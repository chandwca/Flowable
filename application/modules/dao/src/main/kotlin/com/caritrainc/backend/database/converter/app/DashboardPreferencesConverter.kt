package com.caritrainc.backend.database.converter.app

import com.caritrainc.backend.database.model.DashboardPreferences
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class DashboardPreferencesConverter : AttributeConverter<DashboardPreferences, String> {
    private val objectMapper = jacksonObjectMapper()

    override fun convertToDatabaseColumn(attribute: DashboardPreferences): String {
        return objectMapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String): DashboardPreferences {
        return objectMapper.readValue(dbData, DashboardPreferences::class.java)
    }
}