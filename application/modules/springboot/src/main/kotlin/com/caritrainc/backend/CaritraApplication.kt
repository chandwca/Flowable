package com.caritrainc.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
class CaritraApplication

fun main(args: Array<String>) {
    runApplication<CaritraApplication>(*args)
}
