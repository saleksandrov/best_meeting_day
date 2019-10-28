package ru.asv.bmd.base

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class BaseApplication

fun main(args: Array<String>) {
    SpringApplication.run(BaseApplication::class.java, *args)
}