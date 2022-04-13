package com.example.demo.repository.jpa


import com.example.demo.model.Foo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface FooRepository : JpaRepository<Foo,Long> {
    fun findByName(name:String):List<Foo>
}