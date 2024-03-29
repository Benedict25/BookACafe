package com.example.bookacafe.controller

import android.os.StrictMode
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DatabaseHandler {
    var con: Connection? = null
    private var url = "jdbc:mysql://10.0.2.2/bookacafe"
    private var username = "root"
    private var password = ""

    fun connect(): Connection? {
        try {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            con = DriverManager.getConnection(url, username, password)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return con
    }
}