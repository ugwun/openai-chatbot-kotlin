package com.csadovsky.openaichatbotkotlin.dto

import com.cjcrafter.openai.chat.ChatMessage
import java.util.*

data class ChatData(val uuid: UUID?, val messages: MutableList<ChatMessage>?)
