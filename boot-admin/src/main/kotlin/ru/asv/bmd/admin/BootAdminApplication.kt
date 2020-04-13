package ru.asv.bmd.admin

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.PropertySource

@PropertySource(value =
    ["classpath:application.yaml",
    "file:config/bmd-admin/application.yaml"], ignoreResourceNotFound = true)
@SpringBootApplication
@EnableAdminServer
class BootAdminApplication

fun main(args: Array<String>) {
    SpringApplication(BootAdminApplication::class.java).run(*args)
}