package com.example.springbatch

data class Person(var firstName: String? = null, var lastName: String? = null) {
    override fun toString(): String {
        return "firstName: $firstName, lastName: $lastName"
    }
}
