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


    fun CheckOut() {}
    fun Pay() {}


//    companion object {
//        private val user = ActiveUser
////        var con = DatabaseHandler.connect()

    fun ShowHistory() {

//            val transactions: ArrayList<Transaction> = ArrayList()
//            var detailTransactions: ArrayList<DetailTransaction> = ArrayList()
//            var table: Table? = null
//            val query = "SELECT * FROM transactions WHERE memberId = '${user.getId()}'"
//            //Log.d("TAG", query)
//
//            try {
//                val stmt: Statement = con!!.createStatement()
//                val rs: ResultSet = stmt.executeQuery(query)
//
//                while (rs.next()) {
//                    val transaction = Transaction(
//                        rs.getString("transactionId"),
//                        table,
//                        rs.getTimestamp("checkedIn"),
//                        Timestamp.valueOf(rs.getString("checkedOut")),
//                        TransactionEnum.PAID,
//                        detailTransactions
//                    )
//                    transactions.add(transaction)
//                }
//            } catch (e: SQLException) {
//                e.printStackTrace()
//            }
//            return transactions


    }


}