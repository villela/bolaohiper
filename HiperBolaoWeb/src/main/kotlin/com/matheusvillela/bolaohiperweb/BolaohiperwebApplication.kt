package com.matheusvillela.bolaohiperweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class BolaohiperwebApplication

fun main(args: Array<String>) {
    runApplication<BolaohiperwebApplication>(*args)
}
