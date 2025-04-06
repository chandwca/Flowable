package com.caritrainc.backend.model

enum class SequenceType(val type: String) {
    EVENT("EVT"),
    ACCOUNT("ACC"),
    ATTENDEE("ATT"),
    SESSION("SEN"),
    TEMPLATE("TMP"),
    LOCK("LOK"),
    ORDER("ORD"),
    MEMBER("MEM"),
    CLASS("CLS"),
    RESOURCE("RES"),
    PACK("PAC")
}