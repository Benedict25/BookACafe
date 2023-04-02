package com.example.bookacafe.controller

import com.example.bookacafe.model.*
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

object MemberControllers {
    private val user = ActiveUser
    var con = DatabaseHandler.connect()

    fun CheckOut() {}
    fun Pay() {}
    fun ShowHistory(): ArrayList<Transaction> {
        val transactions: ArrayList<Transaction> = ArrayList()
        var detailTransactions: ArrayList<DetailTransaction> = ArrayList()
        val query = "SELECT * FROM transactions WHERE memberId = 'MB003'"

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)

            while (rs.next()) {
                val transaction = Transaction(
                    rs.getString("transactionId"),
                    rs.getString("tableId"),
                    rs.getTimestamp("checkedIn"),
                    rs.getTimestamp("checkedOut"),
                    TransactionEnum.PAID,
                    detailTransactions
                )
                transactions.add(transaction)
            }
        } catch (e: SQLException){
            e.printStackTrace()
        }
        return transactions
    }
}