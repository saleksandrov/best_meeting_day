package ru.asv.bmd.base

import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.PropertySource
import ru.asv.bmd.base.config.YamlPropertySourceFactory

@PropertySource(value = ["classpath:bmd-base-config.yaml", "file:config/bmd-base/bmd-base-config.yaml"],
        factory = YamlPropertySourceFactory::class,
        ignoreResourceNotFound = true)
@SpringBootApplication
open class BaseApplication

fun main(args: Array<String>) {
    SpringApplication(BaseApplication::class.java).apply {
        webApplicationType = WebApplicationType.REACTIVE
    }.run(*args)
}