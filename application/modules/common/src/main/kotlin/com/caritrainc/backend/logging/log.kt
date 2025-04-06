package com.caritrainc.backend.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Class to use as base class for a companion object that adds logging to that class.
 */
open class Loggable(loggerName: String? = null) {
    /**
     * The log instance.
     */
    val log: Logger by lazy {
        loggerName?.let {
            LoggerFactory.getLogger(loggerName)
        } ?: LoggerFactory.getLogger(this.javaClass.canonicalName.replace(".Companion", ""))
    }
}