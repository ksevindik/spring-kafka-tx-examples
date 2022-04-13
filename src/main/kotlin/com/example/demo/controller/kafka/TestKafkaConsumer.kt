package com.example.demo.controller.kafka

import com.example.demo.service.TestService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class TestKafkaConsumer constructor(private val testConsumerService: TestService) {

    @KafkaListener(topics = ["test-topic"],groupId = "demo")
    fun handle(message:String) {
        testConsumerService.process(message)
    }
}