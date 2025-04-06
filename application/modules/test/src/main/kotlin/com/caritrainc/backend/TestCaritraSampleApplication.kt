package com.caritrainc.backend

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<CaritraApplication>().with(TestcontainersConfiguration::class).run(*args)
}
