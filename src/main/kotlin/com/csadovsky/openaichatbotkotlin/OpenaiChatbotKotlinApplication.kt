package com.csadovsky.openaichatbotkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OpenaiChatbotKotlinApplication

fun main(args: Array<String>) {
	runApplication<OpenaiChatbotKotlinApplication>(*args)
}
