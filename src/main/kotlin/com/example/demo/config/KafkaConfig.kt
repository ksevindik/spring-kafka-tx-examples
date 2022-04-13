package com.example.demo.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaConfig {
    @Bean
    fun testTopic() : NewTopic {
        return NewTopic("test-topic",1,1)
    }
}