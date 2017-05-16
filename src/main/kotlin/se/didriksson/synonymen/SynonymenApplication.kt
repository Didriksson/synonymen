package se.didriksson.synonymen

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class SynonymenApplication

fun main(args: Array<String>) {
    SpringApplication.run(SynonymenApplication::class.java, *args)
}
