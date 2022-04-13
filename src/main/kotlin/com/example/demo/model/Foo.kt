package com.example.demo.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Foo(@Column(unique = true) val name:String) {
    @Id
    @GeneratedValue
    val id:Long? = null
}