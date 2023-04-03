package com.example.bookacafe.controller

import android.database.SQLException
import com.example.bookacafe.model.User
import java.sql.ResultSet
import java.sql.Statement

class UserControllers {

    var con = DatabaseHandler.connect()

    fun checkUserType(userId: String): String {
        // Check user type from the first letter of the id
        val firstLetterId = userId.subSequence(0, 1)

        if (firstLetterId == "M") {
            return "MEMBER"
        } else {
            var adminType: String = ""
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
                return  "ADMIN"
            } else {
                return "CASHIER"
            }
        }
    }

    fun setSingleton(user: User, userType: String) {
        ActiveUser.setId(user.userId)
        ActiveUser.setFirstName(user.firstName)
        ActiveUser.setLastName(user.lastName)
        ActiveUser.setEmail(user.email)
        ActiveUser.setPassword(user.password)
        ActiveUser.setType(userType)
    }

}