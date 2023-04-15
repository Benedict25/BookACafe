package com.example.bookacafe.controller

import android.database.SQLException
import com.example.bookacafe.model.Book
import com.example.bookacafe.model.ItemTypeEnum
import com.example.bookacafe.model.adminDataDetails.CashierMenuDetail
import com.example.bookacafe.model.adminDataDetails.TableDummy
import java.sql.ResultSet
import java.sql.Statement
import java.text.DecimalFormat

class CashierControllers {
    private var con = DatabaseHandler.connect()
    private var maxHours = 2
    private var maxHoursMin = maxHours -1
    private val formatter = DecimalFormat("#,###")

    fun updateDetailTransactionStatusMenu(transactionId: String, menuId: String): Boolean {
        return try {
            val query = "UPDATE detail_transactions SET status='SERVED' WHERE menuId='$menuId' AND transactionId='$transactionId'; "
            val stmt: Statement = con!!.createStatement()
            stmt.executeUpdate(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    fun updateDetailTransactionStatusBook(transactionId: String, bookId: String): Boolean {
        return try {
            val query = "UPDATE detail_transactions SET status='SERVED' WHERE bookId='$bookId' AND transactionId='$transactionId';"
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
            val query = "UPDATE tables SET status='AVAILABLE' WHERE tableId='$tableId';"
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
            val query = "UPDATE transactions SET status = 'PAID' WHERE tableId = '$tableId' AND status = 'PENDING'"
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
        val query = "SELECT a.tableName, 10000*if(TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)>$maxHours,TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)-$maxHoursMin,1) as 'tableCost' FROM tables a JOIN transactions b ON a.tableId = b.tableId WHERE b.status = 'PENDING' AND a.tableName = '$tableName' GROUP BY a.tableId;"
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
        val query1 = "SELECT count(c.menuId) as 'counter' FROM tables a JOIN transactions b ON a.tableId = b.tableId JOIN detail_transactions c ON c.transactionId = b.transactionId WHERE b.transactionId = '$transactionId' AND c.status = 'NOT_SERVED' GROUP BY c.transactionId;"
        val query2 = "SELECT count(c.bookId) as 'counter' FROM tables a JOIN transactions b ON a.tableId = b.tableId JOIN detail_transactions c ON c.transactionId = b.transactionId WHERE b.transactionId = '$transactionId' AND c.status = 'NOT_SERVED' GROUP BY c.transactionId;"
        try{
            val stmt: Statement = con!!.createStatement()
            val rs1: ResultSet = stmt.executeQuery(query1)
            val rs2: ResultSet = stmt.executeQuery(query2)
            founded = rs1.next()
            if (founded) {
                founded = rs2.next()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return founded
    }

    fun getTableData(tableName: String): TableDummy {
        lateinit var tableData: TableDummy
        val query = "SELECT a.tableId, a.tableName, 10000*if(TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)>$maxHours,TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)-$maxHoursMin,1) as 'tableCost' FROM tables a JOIN transactions b ON a.tableId = b.tableId WHERE b.status = 'NOT_PAID' OR b.status = 'PENDING' AND a.tableName = '$tableName' GROUP BY a.tableId;"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                tableData = TableDummy(
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
        val query = "SELECT b.transactionId FROM tables a JOIN transactions b ON a.tableId = b.tableId WHERE b.status = 'NOT_PAID' OR b.status = 'PENDING' AND a.tableName = '$tableName' GROUP BY a.tableId;"
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

    fun getOrderedMenuData(tableName: String, condition: Boolean): ArrayList<CashierMenuDetail> {
        val orderedMenus: ArrayList<CashierMenuDetail> = ArrayList()
        var query = "SELECT a.menuId, a.name, a.price, b.menuQuantity, b.menuQuantity*a.price as \"menuCost\" FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId JOIN transactions c ON c.transactionId = b.transactionId JOIN tables d ON d.tableId=c.tableId WHERE d.tableName = '$tableName' AND c.status='PENDING' GROUP BY a.menuId;"
        if (condition) {
            query = "SELECT a.menuId, a.name, a.price, b.menuQuantity, b.menuQuantity*a.price as \"menuCost\" FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId JOIN transactions c ON c.transactionId = b.transactionId JOIN tables d ON d.tableId=c.tableId WHERE d.tableName = '$tableName' AND c.status='NOT_PAID' AND b.status='NOT_SERVED' GROUP BY a.menuId;"
        }
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val menu = CashierMenuDetail(
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

    fun getOrderedBookData(tableName: String, condition: Boolean): ArrayList<Book> {
        val books: ArrayList<Book> = ArrayList()
        var query = "SELECT a.bookId, a.title FROM books a JOIN detail_transactions b ON a.bookId = b.bookId JOIN transactions c ON c.transactionId = b.transactionId JOIN tables d ON d.tableId=c.tableId WHERE d.tableName = '$tableName' AND c.status='PENDING' GROUP BY a.bookId;"
        if (condition) {
            query = "SELECT a.bookId, a.title FROM books a JOIN detail_transactions b ON a.bookId = b.bookId JOIN transactions c ON c.transactionId = b.transactionId JOIN tables d ON d.tableId=c.tableId WHERE d.tableName = '$tableName' AND c.status='NOT_PAID' AND b.status='NOT_SERVED' GROUP BY a.bookId;"
        }
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val book = Book(
                    rs.getString("bookId"),
                    rs.getString("title"),
                    "","","","", ItemTypeEnum.AVAILABLE
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
        val querySeat = "SELECT 10000*if(TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)>$maxHours,TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)-$maxHoursMin,1) as 'tableCost' FROM tables a JOIN transactions b ON a.tableId = b.tableId WHERE a.tableName = '$tableName' AND b.status = 'PENDING';"
        val queryMenuOrdered = "SELECT b.menuQuantity*a.price as \"menuCost\" FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId JOIN transactions c ON b.transactionId = c.transactionId JOIN tables d ON d.tableId= c.tableId WHERE d.tableName = '$tableName' AND c.status = 'PENDING';"
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