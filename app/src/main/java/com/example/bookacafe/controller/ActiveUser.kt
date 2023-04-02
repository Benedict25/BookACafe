package com.example.bookacafe.controller

object ActiveUser {

    // Singleton to save the id & the name of a logged in user

    private var id: String = "NoId"
    private var firstName: String = "NoFirstName"
    private var lastName: String = "NoLastName"
    private var email: String = "NoEmail"
    private var password: String = "NoPassword"

    fun setId(id: String) {
        this.id = id
    }

    fun setFirstName(firstName: String) {
        this.firstName = firstName
    }

    fun setLastName(lastName: String) {
        this.lastName = lastName
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun getId(): String {
        return this.id
    }

    fun getFirstName(): String {
        return this.firstName
    }

    fun getLastName(): String {
        return this.lastName
    }

    fun getEmail(): String {
        return this.email
    }

    fun getPassword(): String {
        return this.password
    }


}