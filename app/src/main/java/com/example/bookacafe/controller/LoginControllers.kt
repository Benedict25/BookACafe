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
        val query = "SELECT * FROM users WHERE email = '$inputEmail' AND password = '$inputPassword'"

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)

            while (rs.next()) {
                 user = User(
                    rs.getString("userId"),
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
                ActiveUser.setFirstName(user.firstName)
                ActiveUser.setLastName(user.lastName)
                ActiveUser.setEmail(user.email)
                ActiveUser.setPassword(user.password)
                
                // Check userType dari alphabet depan userId
                val userId = user.userId.subSequence(0, 1)
                ActiveUser.setType(checkUserType(user, userId.toString()))
                
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

    fun checkUserType(user: User, userId: String): String {
        if (userId == "M") {
            return "MEMBER"
        } else {
            var adminType: String = ""
            val query = "SELECT adminType FROM admins WHERE adminId = '${user.userId}'"

            try {
                val stmt: Statement = con!!.createStatement()
                val rs: ResultSet = stmt.executeQuery(query)
                while (rs.next()) {
                    adminType = rs.getString("adminType")
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            if (adminType == "ADMIN") {
                return  "ADMIN"
            } else {
                return "CASHIER"
            }
        }
    }

}