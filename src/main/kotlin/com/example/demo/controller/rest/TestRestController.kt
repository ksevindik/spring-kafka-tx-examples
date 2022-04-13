package com.example.demo.controller.rest

import com.example.demo.repository.kafka.TestProducer
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest/tests")
class TestRestController constructor(private val testProducer: TestProducer) {
    @PostMapping
    fun publish(@RequestBody testRequest: TestRequest) : ResponseEntity<Void> {
        testProducer.publish(testRequest)

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}

data class TestRequest(val message:String,val fail:Boolean)