package com.csadovsky.openaichatbotkotlin.controller

import com.cjcrafter.openai.OpenAI
import com.cjcrafter.openai.chat.ChatMessage
import com.cjcrafter.openai.chat.ChatMessage.Companion.toSystemMessage
import com.cjcrafter.openai.chat.ChatRequest
import com.cjcrafter.openai.chat.ChatUser
import com.csadovsky.openaichatbotkotlin.dto.ChatData
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping("/chat")
class ChatController {

    @Value("\${openai.api.key}")
    private lateinit var apiKey: String

    @Value("\${chat.system_message}")
    private lateinit var SYSTEM_MESSAGE: String

    private lateinit var openAI: OpenAI
    private val conversations = mutableMapOf<UUID, MutableList<ChatMessage>>()

    @PostConstruct
    fun initOpenAI() {
        openAI = OpenAI(apiKey)
    }

    @GetMapping
    fun showChatUI(): String {
        // Return the Thymeleaf template name for the chatbot UI
        return "chat"
    }

    @PostMapping("/api", consumes = ["application/json"], produces = ["application/json"])
    @ResponseBody
    fun chatAPI(@RequestBody chatData: ChatData): ChatData {
        val messages = chatData.messages ?: mutableListOf()
        if (messages.isEmpty() || messages[0].role != ChatUser.SYSTEM) {
            messages.add(0, SYSTEM_MESSAGE.toSystemMessage())
        }

        val request = ChatRequest.builder()
            .model("gpt-3.5-turbo")
            .messages(messages)
            .build()

        val response = openAI.createChatCompletion(request)
        messages.add(response[0].message)

        return ChatData(null, messages)
    }

    @PostMapping("/api/store", consumes = ["application/json"], produces = ["application/json"])
    @ResponseBody
    fun chatAPIWithStore(@RequestBody chatData: ChatData, session: HttpSession): ChatData {
        val uuid = session.getAttribute("uuid") as? UUID ?: UUID.randomUUID().apply { session.setAttribute("uuid", this) }
        val messages = conversations.getOrPut(uuid) { chatData.messages ?: mutableListOf() }

        if (messages.isEmpty() || messages[0].role != ChatUser.SYSTEM) {
            messages.add(0, SYSTEM_MESSAGE.toSystemMessage())
        }

        val request = ChatRequest.builder()
            .model("gpt-3.5-turbo")
            .messages(messages)
            .build()

        val response = openAI.createChatCompletion(request)
        messages.add(response[0].message)

        return ChatData(uuid, messages)
    }

    @GetMapping("/load/{uuid}", produces = ["application/json"])
    @ResponseBody
    fun loadStoredConversation(@PathVariable uuid: UUID): ChatData {
        val messages = conversations[uuid]
        return ChatData(uuid, messages)
    }
}