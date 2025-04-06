package com.caritrainc.backend

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

const val APP_ENV = "APP_ENV"

val jacksonObjectMapper = jacksonObjectMapper().apply {
    registerModules(JavaTimeModule())
    // Disabling the mapper object to deserialize
    // properties which cannot be mapped to java
    // class
    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
}

val APP_ENV_ID: String by lazy {
    return@lazy System.getProperty(APP_ENV, "Dev").toCharArray().first().toString()
}