package com.example.bookacafe.controller

object ActiveUser {

    // Singleton

    private var id: String = "NoId"
    private var name: String = "NoUser"
    private var userType: String = "NoType" // MEMBER, ADMIN, CASHIER

    fun setId(id: String){
        this.id = id
    }

    fun setName(name: String){
        this.name = name
    }

    fun setType(userType: String){
        this.userType = userType
    }

    fun getId(): String {
        return this.id
    }

    fun getName(): String {
        return this.name
    }

    fun getType(): String {
        return this.userType
    }

}