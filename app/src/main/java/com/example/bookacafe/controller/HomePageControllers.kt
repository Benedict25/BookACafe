package com.example.bookacafe.controller

import android.database.SQLException
import java.sql.ResultSet
import java.sql.Statement

class HomePageControllers {
    var con = DatabaseHandler.connect()

    fun getName(): String {
        var firstName: String = ""
        var lastName: String = ""
        var name: String = ""
        val query = "SELECT firstName,lastName FROM users WHERE userId = '${ActiveUser.getId()}'"

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                firstName = rs.getString("firstName")
                lastName = rs.getString("lastName")
                name = "${firstName} " + "${lastName}"
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return name
    }

    fun getTime(): String {
        var time: String = ""
        val query = "SELECT checkedIn FROM transactions WHERE memberId = '${ActiveUser.getId()}' and status = 'NOT_PAID'"

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val checkedIn = rs.getTimestamp("checkedIn")
                val timeCheckIn = checkedIn.toString()
                time = timeCheckIn.substring(11, 16)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            return ""
        }

        return time
    }

}