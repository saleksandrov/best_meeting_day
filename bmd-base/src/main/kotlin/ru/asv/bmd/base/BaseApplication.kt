package ru.asv.bmd.base

import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.PropertySource

@PropertySource(value =
    ["classpath:application.yaml",
    "file:config/bmd-base/application.yaml"], ignoreResourceNotFound = true)
@SpringBootApplication
open class BaseApplication

fun main(args: Array<String>) {
    SpringApplication(BaseApplication::class.java).apply {
        webApplicationType = WebApplicationType.REACTIVE
    }.run(*args)
}