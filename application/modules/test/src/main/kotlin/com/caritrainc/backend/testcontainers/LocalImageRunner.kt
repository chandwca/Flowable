//package com.caritrainc.backend.testcontainers
//
//fun main() {
//    val obj = TestContainerInitializer()
//    try {
//        println("Running test container in local")
//        obj.reset()
//        obj.info()
//        // Will run for a day
//        Thread.sleep(1000 * 60 * 60 * 24)
//    } catch (e: Exception) {
//        e.printStackTrace()
//    } finally {
//        obj.stop()
//    }
//}