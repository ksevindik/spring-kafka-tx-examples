package com.example.demo.service

import com.example.demo.controller.rest.TestRequest
import org.springframework.stereotype.Service

@Service
class TestService {
    fun process(message:String) {
        println(">>>$message consumed...")
    }
}