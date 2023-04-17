package com.example.bookacafe.controller

import android.database.SQLException
import com.example.bookacafe.model.*
import com.example.bookacafe.model.CashierBookDetails
import com.example.bookacafe.model.CashierMenuDetails
import com.example.bookacafe.model.AdminTableDetails
import java.sql.ResultSet
import java.sql.Statement
import java.text.DecimalFormat

class CashierControllers {
    private var con = DatabaseHandler.connect()
    private var maxHours = 2
    private var maxHoursMin = maxHours -1
    private val formatter = DecimalFormat("#,###")

    fun updateDetailTransactionStatus(detailTransactionId: Int): Boolean {
        return try {
            val query = "UPDATE detail_transactions SET status='${DetailTransEnum.SERVED}' WHERE detailTransactionId='$detailTransactionId'";
            val stmt: Statement = con!!.createStatement()
            stmt.executeUpdate(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    fun updateTableStatus(tableId: String): Boolean {
        return try {
            val query = "UPDATE tables SET status='${TableTypeEnum.AVAILABLE}' WHERE tableId='$tableId';"
            val stmt: Statement = con!!.createStatement()
            stmt.executeUpdate(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    fun updateTransactionStatus(tableId: String): Boolean {
        return try {
            val query = "UPDATE transactions SET status = '${TransactionEnum.PAID}' WHERE tableId = '$tableId' AND status = '${TransactionEnum.PENDING}'"
            val stmt: Statement = con!!.createStatement()
            stmt.executeUpdate(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    fun getTableInTransaction(tableName:String): Boolean {
        var founded = false
        val query = "SELECT a.tableName, 10000*if(TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)>$maxHours,TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)-$maxHoursMin,1) as 'tableCost' FROM tables a JOIN transactions b ON a.tableId = b.tableId WHERE b.status = '${TransactionEnum.PENDING}' AND a.tableName = '$tableName' GROUP BY a.tableId;"
        try{
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            founded = rs.next()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return founded
    }

    fun getNotServedMenu(transactionId: String): Boolean {
        var founded = false
        val query = "SELECT count(c.detailTransactionId) as 'counter' FROM tables a JOIN transactions b ON a.tableId = b.tableId JOIN detail_transactions c ON c.transactionId = b.transactionId WHERE b.transactionId = '$transactionId' AND c.status = '${DetailTransEnum.NOT_SERVED}' GROUP BY c.transactionId;"
        try{
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            founded = rs.next()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return founded
    }

    fun getTableData(tableName: String): AdminTableDetails {
        lateinit var tableData: AdminTableDetails
        val query = "SELECT a.tableId, a.tableName, 10000*if(TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)>$maxHours,TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)-$maxHoursMin,1) as 'tableCost' FROM tables a JOIN transactions b ON a.tableId = b.tableId WHERE a.tableName = '$tableName' AND (b.status = '${TransactionEnum.NOT_PAID}' OR b.status = '${TransactionEnum.PENDING}') GROUP BY a.tableId;"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                tableData = AdminTableDetails(
                    rs.getString("tableId"),
                    rs.getString("tableName"),
                    formatter.format(rs.getInt("tableCost"))
                )
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return tableData
    }

    fun getTransactionId(tableName: String): String {
        lateinit var transId: String
        val query = "SELECT b.transactionId FROM tables a JOIN transactions b ON a.tableId = b.tableId WHERE a.tableName = '$tableName' AND (b.status = '${TransactionEnum.NOT_PAID}' OR b.status = '${TransactionEnum.PENDING}') GROUP BY a.tableId;"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                transId = rs.getString("transactionId")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return transId
    }

    fun getOrderedMenuData(tableName: String, condition: Boolean): ArrayList<CashierMenuDetails> {
        val orderedMenus: ArrayList<CashierMenuDetails> = ArrayList()
        var query = "SELECT b.detailTransactionId, a.menuId, a.name, a.price, sum(b.menuQuantity) as \"menuQuantity\", sum(b.menuQuantity*a.price) as \"menuCost\" FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId JOIN transactions c ON c.transactionId = b.transactionId JOIN tables d ON d.tableId=c.tableId WHERE d.tableName = '$tableName' AND c.status='${TransactionEnum.PENDING}' GROUP BY a.menuId;"
        if (condition) {
            query = "SELECT b.detailTransactionId, a.menuId, a.name, a.price, b.menuQuantity, b.menuQuantity*a.price as \"menuCost\" FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId JOIN transactions c ON c.transactionId = b.transactionId JOIN tables d ON d.tableId=c.tableId WHERE d.tableName = '$tableName' AND c.status='${TransactionEnum.NOT_PAID}' AND b.status='${DetailTransEnum.NOT_SERVED}' GROUP BY  b.detailTransactionId;"
        }
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val menu = CashierMenuDetails(
                    rs.getInt("detailTransactionId"),
                    rs.getString("menuId"),
                    rs.getString("name"),
                    formatter.format(rs.getInt("price")),
                    rs.getString("menuQuantity"),
                    formatter.format(rs.getInt("menuCost"))
                )
                orderedMenus.add(menu)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return orderedMenus
    }

    fun getOrderedBookData(tableName: String, condition: Boolean): ArrayList<CashierBookDetails> {
        val books: ArrayList<CashierBookDetails> = ArrayList()
        var query = "SELECT b.detailTransactionId, a.bookId, a.title FROM books a JOIN detail_transactions b ON a.bookId = b.bookId JOIN transactions c ON c.transactionId = b.transactionId JOIN tables d ON d.tableId=c.tableId WHERE d.tableName = '$tableName' AND c.status='${TransactionEnum.PENDING}' GROUP BY a.bookId;"
        if (condition) {
            query = "SELECT b.detailTransactionId, a.bookId, a.title FROM books a JOIN detail_transactions b ON a.bookId = b.bookId JOIN transactions c ON c.transactionId = b.transactionId JOIN tables d ON d.tableId=c.tableId WHERE d.tableName = '$tableName' AND c.status='${TransactionEnum.NOT_PAID}' AND b.status='${DetailTransEnum.NOT_SERVED}' GROUP BY a.bookId;"
        }
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val book = CashierBookDetails (
                    rs.getInt("detailTransactionId"),
                    rs.getString("bookId"),
                    rs.getString("title")
                )
                books.add(book)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return books
    }

    fun getTableCosts(tableName: String): Int {
        var income = 0
        val querySeat = "SELECT 10000*if(TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)>$maxHours,TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)-$maxHoursMin,1) as 'tableCost' FROM tables a JOIN transactions b ON a.tableId = b.tableId WHERE a.tableName = '$tableName' AND b.status = '${TransactionEnum.PENDING}';"
        val queryMenuOrdered = "SELECT sum(b.menuQuantity*a.price) as \"menuCost\" FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId JOIN transactions c ON b.transactionId = c.transactionId JOIN tables d ON d.tableId= c.tableId WHERE d.tableName = '$tableName' AND c.status = '${TransactionEnum.PENDING}';"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs1: ResultSet = stmt.executeQuery(querySeat)
            val rs2: ResultSet = stmt.executeQuery(queryMenuOrdered)
            while (rs1.next()) {
                income += rs1.getInt("tableCost")
            }
            while (rs2.next()) {
                income += rs2.getInt("menuCost")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return income
    }
}