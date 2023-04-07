package com.example.bookacafe.controller

import android.database.SQLException
import com.example.bookacafe.model.Table
import com.example.bookacafe.model.TableTypeEnum
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class TableControllers {
    private val user = ActiveUser
    var con = DatabaseHandler.connect()

    fun getTableData(): ArrayList<Table> {
        val tables: ArrayList<Table> = ArrayList()
        val query = "SELECT * from tables WHERE status = '${TableTypeEnum.AVAILABLE}'"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val table = Table(
                    rs.getString("tableId"),
                    rs.getString("tableName"),
                    rs.getString("room"),
                    TableTypeEnum.AVAILABLE
                )
                tables.add(table)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return tables
    }
    fun addTable(table: Table): Boolean {
        return try {
            val query = "INSERT INTO tables VALUES(?,?,?,?)"
            val stmt: PreparedStatement = con!!.prepareStatement(query)
            val tableId = createTableId()
            stmt.setString(1, tableId)
            stmt.setString(2, table.tableName)
            stmt.setString(3, table.room)
            stmt.setString(4, TableTypeEnum.AVAILABLE.toString())
            stmt.executeUpdate()
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }
    fun editTable(table: Table): Boolean {
        return try {
            val query = "UPDATE tables SET tableName = '${table.tableName}', room = '${table.room}'" +
                    "WHERE tableId = '${table.tableId}'"
            val stmt: Statement = con!!.createStatement()
            stmt.executeUpdate(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }
    fun deleteTable(tableId: String): Boolean {
        return try {
            val query =
                "UPDATE tables SET status = '${TableTypeEnum.BLOCKED}' WHERE tableId = '$tableId'"
            val stmt: Statement = con!!.createStatement()
            stmt.executeUpdate(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
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
            val query =
                "UPDATE carts SET tableId = '$tableId' WHERE cartId = '$cartId'"
            val stmt2: Statement = con!!.createStatement()
            stmt2.executeUpdate(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    private fun createTableId(): String {
        val query = "SELECT tableId FROM tables ORDER BY tableId DESC LIMIT 1"
        var newestId = String()
        val returnId: String

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                newestId = rs.getString("tableId")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        val extractNumber = newestId.subSequence(1, 3)
        var number = extractNumber.toString().toInt()
        number += 1

        if (number < 10) {
            returnId = "T0$number"
        } else {
            returnId = "T$number"
        }

        return returnId
    }
}