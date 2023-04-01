package com.example.bookacafe.controller

object ActiveUser {

    // Singleton to save the id & the name of a logged in user

    private var id: String = "NoId"
    private var name: String = "NoUser"

    fun setId(id: String){
        this.id = id
    }

    fun setName(name: String){
        this.name = name
    }

    fun getId(): String {
        return this.id
    }

    fun getName(): String {
        return this.name
    }

}