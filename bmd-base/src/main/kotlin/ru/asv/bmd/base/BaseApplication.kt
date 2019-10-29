package ru.asv.bmd.base

import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class BaseApplication

fun main(args: Array<String>) {
    SpringApplication(BaseApplication::class.java).apply {
        webApplicationType = WebApplicationType.REACTIVE
    }.run(*args)
}