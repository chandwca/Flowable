package com.caritrainc.backend.database.converter.app

import com.caritrainc.backend.database.model.BusinessLimits
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class BusinessLimitsConverter : AttributeConverter<BusinessLimits, String> {
    private val objectMapper = jacksonObjectMapper()

    override fun convertToDatabaseColumn(attribute: BusinessLimits): String {
        return objectMapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String): BusinessLimits {
        return objectMapper.readValue(dbData, BusinessLimits::class.java)
    }
}