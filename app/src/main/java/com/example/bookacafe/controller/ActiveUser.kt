package com.example.bookacafe.controller

import com.example.bookacafe.model.Transaction


object ActiveUser {

    // Singleton

    private var id: String = "NoId"
    private var firstName: String = "NoFirstName"
    private var lastName: String = "NoLastName"
    private var email: String = "NoEmail"
    private var password: String = "NoPassword"
    private var activeTransaction: Transaction? = null // get active transaction
    private var userType: String = "NoType" // MEMBER, ADMIN, CASHIER
    
    // Setter
    
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

    fun setType(userType: String){
        this.userType = userType
    }

    fun setActiveTransaction(activeTransaction: Transaction){
        this.activeTransaction = activeTransaction
    }
    
    // Getter

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
    
     fun getType(): String {
        return this.userType
    }

    fun getActiveTransaction(): Transaction? {
        return this.activeTransaction
    }
}