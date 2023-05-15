# Kotlin-SpringBoot-Thymeleaf Chatbot

This project is a simple chatbot application built with Kotlin, Spring Boot, Thymeleaf, and Gradle Kotlin DSL. It uses the OpenAI API to generate responses based on user input.

## Features

- Chat with the chatbot through a web interface
- Store and load conversations using UUIDs
- Communicate with the OpenAI API to generate chatbot responses

## Prerequisites

- JDK 17
- Gradle

## Installation

1. Clone the repository:

```bash
git clone https://github.com/ugwun/openai-chatbot-kotlin.git
```

2. Navigate to the project directory:

```bash
cd openai-chatbot-kotlin
```

3. Set the OpenAI API key in the application.properties file:
```
openai.api.key=your_openai_api_key
```

## Running the Application
To run the application, execute the following command in the project directory:

```bash
./gradlew bootRun
```

The application will start on http://localhost:8080/chat.

## Usage
1. Open a web browser and navigate to http://localhost:8080/chat.
2. Type a message in the input field and click the "Send" button or press "Enter" to send the message.
3. The chatbot will respond with a generated message based on your input.
4. To load a stored conversation, enter the UUID in the UUID input field and click the "Load" button or press "Enter".
5. To clear the conversation, click the "Clear" button.

## License
This project is licensed under the Apache License, Version 2.0. See the [LICENSE](LICENSE) file for details.