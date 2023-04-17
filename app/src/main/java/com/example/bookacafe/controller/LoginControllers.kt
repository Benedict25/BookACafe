package com.example.bookacafe.controller

import android.database.SQLException
import android.util.Log
import com.example.bookacafe.model.Transaction
import com.example.bookacafe.model.User
import java.sql.ResultSet
import java.sql.Statement

class LoginControllers {
    private var con = DatabaseHandler.connect()

    fun getLoginData(inputEmail: String, inputPassword: String): Boolean {
        var user = User("", "", "", "", "")
        val query =
            "SELECT * FROM users WHERE email = '$inputEmail' AND password = '$inputPassword'"

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

            if (isLoggedIn) {
                val control = UserControllers()
                val userType = control.checkUserType(user.userId)
                val activeTransactionId = TransactionControllers.getActiveTransactionId(user.userId)
                var activeTransaction: Transaction? = null
                if (activeTransactionId == null) {
                    control.setSingleton(user, userType, null)
                } else {
                    activeTransaction =
                        TransactionControllers.getTransactionDetail(activeTransactionId)
                    control.setSingleton(user, userType, activeTransaction)
                }

                if (activeTransactionId != null) {
                    Log.d("TAG", activeTransactionId)
                } else {
                    Log.d("TAG", "no active trans")
                }
                return true
            } else {
                return false
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        }
    }

    private fun checkLoginData(user: User, inputEmail: String, inputPassword: String): Boolean {
        return inputEmail == user.email && inputPassword == user.password
    }

}