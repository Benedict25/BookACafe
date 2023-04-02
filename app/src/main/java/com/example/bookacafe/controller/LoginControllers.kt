package com.example.bookacafe.controller

import android.database.SQLException
import android.util.Log
import com.example.bookacafe.model.User
import java.sql.ResultSet
import java.sql.Statement

class LoginControllers {

    var con = DatabaseHandler.connect()

    fun getLoginData(inputEmail: String, inputPassword: String): Boolean {
        var user: User = User("", "", "", "", "")
        val query = "SELECT * FROM members WHERE email = '$inputEmail' AND password = '$inputPassword'"

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)

            while (rs.next()) {
                 user = User(
                    rs.getString("memberId"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("password")
                )
            }

            // Recheck Login Data
            val isLoggedIn: Boolean = checkLoginData(user, inputEmail, inputPassword)

            if (isLoggedIn){
                ActiveUser.setId(user.userId)
                ActiveUser.setName(user.firstName)
                return true
            } else {
                return false
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        }
    }

    fun checkLoginData(user: User, inputEmail: String, inputPassword: String): Boolean{
        return inputEmail == user.email && inputPassword == user.password
    }

}