package com.example.bookacafe.controller

import android.database.SQLException
import com.example.bookacafe.model.User
import java.sql.ResultSet
import java.sql.Statement

class RegisterControllers {
    private var con = DatabaseHandler.connect()

    fun registerUser(user: User, checkPassword: String): Boolean {
        if (user.password == checkPassword) {
            val control = CartControllers()
            val cartId = control.createCartId()
            val memberId = createMemberId()
            val query = "INSERT INTO users VALUES ('$memberId', '${user.firstName}', '${user.lastName}', '${user.email}', '${user.password}')"
            val query2 = "INSERT INTO members VALUES ('$memberId', 'ACTIVE')"
            val query3 = "INSERT INTO carts VALUES ('$cartId', '$memberId', NULL)"

            return try {
                val stmt: Statement = con!!.createStatement()
                stmt.executeQuery(query)
                stmt.executeQuery(query2)
                stmt.executeQuery(query3)
                 true
            } catch (e: SQLException) {
                e.printStackTrace()
                false
            }
        } else {
            return false
        }
    }

    private fun createMemberId(): String {

        val query = "SELECT memberId FROM members ORDER BY memberId ASC"
        var newestId = String()
        var returnId = String()

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                // Bakal ke overwrite terus sampe id paling baru di DB
                newestId = rs.getString("memberId")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        // Ambil angkanya aja dari memberId terus di +1
        val extractNumber = newestId.subSequence(2, 5)
        var number = extractNumber.toString().toInt()
        number += 1

        if (number < 10) {
            returnId = "MB00$number"
        } else if (number < 100) {
            returnId = "MB0$number"
        } else {
            returnId = "MB$number"
        }

        return returnId
    }

    fun checkAccountExistence(emailCheck: String): Boolean {
        val query = "SELECT * FROM users WHERE email = '$emailCheck'"

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)

            if (rs.first() == false) { // No result ; Email tidak ada di database
                return false
            }

        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return true
    }
}