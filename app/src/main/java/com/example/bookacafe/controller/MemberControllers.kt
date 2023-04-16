package com.example.bookacafe.controller

import android.util.Log
import com.example.bookacafe.model.*
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MemberControllers {
    private val user = ActiveUser
    private var con = DatabaseHandler.connect()

    fun checkOut() {}
    fun pay() {}
    fun showHistory() {}
}