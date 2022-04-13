package com.example.demo.repository.kafka

import com.example.demo.controller.rest.TestRequest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TestProducer constructor(private val kafkaTemplate: KafkaTemplate<String,String>) {
    @Transactional(transactionManager = "kafkaTransactionManager")
    fun publish(testRequest: TestRequest) {
        kafkaTemplate.send("test-topic",testRequest.message).get()
        if(testRequest.fail) {
            throw RuntimeException("fail signal received")
        }
    }
}