package com.example.bookacafe.controller

import android.database.SQLException
import com.example.bookacafe.model.Table
import com.example.bookacafe.model.TableTypeEnum
import com.example.bookacafe.model.TransactionEnum
import java.sql.ResultSet
import java.sql.Statement

class TableControllers {
    private val user = ActiveUser
    private var con = DatabaseHandler.connect()

    fun getTableData(): ArrayList<Table> {
        val tables: ArrayList<Table> = ArrayList()
        val query = "SELECT * from tables"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            var status = TableTypeEnum.AVAILABLE
            while (rs.next()) {
                when (rs.getString("status")) {
                    "AVAILABLE" -> status = TableTypeEnum.AVAILABLE
                    "BOOKED" -> status = TableTypeEnum.BOOKED
                    "BLOCKED" -> status = TableTypeEnum.BLOCKED
                }
                val table = Table(
                    rs.getString("tableId"),
                    rs.getString("tableName"),
                    rs.getString("room"),
                    status
                )
                tables.add(table)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return tables
    }

    fun addTableToCart(tableId: String): Boolean {
        return try {
            var cartId = ""
            val query1 = "SELECT cartId FROM carts WHERE memberId = '${user.getId()}'"
            val stmt1: Statement = con!!.createStatement()
            val rs: ResultSet = stmt1.executeQuery(query1)
            while (rs.next()) {
                cartId = rs.getString("cartId")
            }

            var tableIdFromDatabase = String()
            val query2 = "SELECT tableId FROM carts WHERE cartId = '$cartId'"
            val stmt2: Statement = con!!.createStatement()
            val rs2: ResultSet = stmt2.executeQuery(query2)
            if (rs2.next()) {
                if (rs2.getObject("tableId") == null) {
                    tableIdFromDatabase = "NULL"
                }
            }

            val query3 = "SELECT tableId FROM transactions WHERE memberId = '${user.getId()}' AND status = '${TransactionEnum.NOT_PAID}'"
            val stmt3: Statement = con!!.createStatement()
            val rs3: ResultSet = stmt3.executeQuery(query3)

            if (tableIdFromDatabase != "NULL" || rs3.first()) {
                return false
            } else {
                val query4 = "UPDATE carts SET tableId = '$tableId' WHERE cartId = '$cartId'"
                val stmt4: Statement = con!!.createStatement()
                stmt4.executeUpdate(query4)
                true
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }
}