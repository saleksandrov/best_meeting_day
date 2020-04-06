package ru.asv.bmd.admin

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@EnableAdminServer
open class BootAdminApplication

fun main(args: Array<String>) {
    SpringApplication(BootAdminApplication::class.java).run(*args)
}