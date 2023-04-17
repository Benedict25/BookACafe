package com.example.bookacafe.controller

import android.database.SQLException
import com.example.bookacafe.model.Transaction
import com.example.bookacafe.model.User
import java.sql.ResultSet
import java.sql.Statement

class UserControllers {
    private var con = DatabaseHandler.connect()

    fun checkUserType(userId: String): String {
        // Check user type from the first letter of the id
        val firstLetterId = userId.subSequence(0, 1)

        if (firstLetterId == "M") {
            return "MEMBER"
        } else {
            var adminType = ""
            val query = "SELECT adminType FROM admins WHERE adminId = '$userId'"

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
                return "ADMIN"
            } else {
                return "CASHIER"
            }
        }
    }

    fun setSingleton(user: User, userType: String, activeTransaction: Transaction?) {
        ActiveUser.setId(user.userId)
        ActiveUser.setFirstName(user.firstName)
        ActiveUser.setLastName(user.lastName)
        ActiveUser.setEmail(user.email)
        ActiveUser.setPassword(user.password)
        ActiveUser.setType(userType)
        if (activeTransaction != null) {
            ActiveUser.setActiveTransaction(activeTransaction)
        }
    }

    fun setSingletonGoogle(inputEmail: String) {
        var user = User("", "", "", "", "")
        val query = "SELECT * FROM users WHERE email = '$inputEmail'"

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

            val userType = checkUserType(user.userId)
            val activeTransactionId = TransactionControllers.getActiveTransactionId(user.userId)
            var activeTransaction: Transaction? = null
            if (activeTransactionId == null) {
                setSingleton(user, userType, null)
            } else {
                activeTransaction = TransactionControllers.getTransactionDetail(activeTransactionId)
                setSingleton(user, userType, activeTransaction)
            }

//            val activeTransactionIdId
//            setSingleton(user, userType)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

}