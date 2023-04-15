package com.example.bookacafe.controller

import android.database.SQLException
import com.example.bookacafe.model.User
import java.sql.ResultSet
import java.sql.Statement

class LoginControllers {
    private var con = DatabaseHandler.connect()

    fun getLoginData(inputEmail: String, inputPassword: String): Boolean {
        var user = User("", "", "", "", "")
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
                val control = UserControllers()
                val userType = control.checkUserType(user.userId)

                control.setSingleton(user, userType)
                
                return true
            } else {
                return false
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        }
    }

    private fun checkLoginData(user: User, inputEmail: String, inputPassword: String): Boolean{
        return inputEmail == user.email && inputPassword == user.password
    }

}