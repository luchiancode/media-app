package com.mediaapp.mediaapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class MediaAppApplication

fun main(args: Array<String>) {
    runApplication<MediaAppApplication>(*args)
}
