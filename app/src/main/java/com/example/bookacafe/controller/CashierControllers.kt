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
    var con = DatabaseHandler.connect()
    var maxHours = 2
    var maxHoursMin = maxHours -1
    val formatter = DecimalFormat("#,###")

    fun updateTransactionStatus(tableId: String): Boolean {
        return try {
            val query = "UPDATE transactions SET status = 'PAID' WHERE tableId = '$tableId' AND status = 'NOT_PAID'"
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
        val query = "SELECT a.tableName, 10000*if(TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)>$maxHours,TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)-$maxHoursMin,1) as 'tableCost' FROM tables a JOIN transactions b ON a.tableId = b.tableId WHERE b.status = 'NOT_PAID' AND a.tableName = '$tableName' GROUP BY a.tableId;"
        try{
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            founded = rs.next()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return founded
    }
    fun getTableData(tableName: String): TableDummy {
//        SELECT a.tableName, 10000*if(TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)>2,TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)-1,1) as 'tableCost' FROM tables a JOIN transactions b ON a.tableId = b.tableId WHERE b.status = 'NOT_PAID' AND a.tableName = 'A1' GROUP BY a.tableId;
        lateinit var tableData: TableDummy
        val query = "SELECT a.tableId, a.tableName, 10000*if(TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)>$maxHours,TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)-$maxHoursMin,1) as 'tableCost' FROM tables a JOIN transactions b ON a.tableId = b.tableId WHERE b.status = 'NOT_PAID' AND a.tableName = '$tableName' GROUP BY a.tableId;"
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

    fun getOrderedMenuData(tableName: String): ArrayList<CashierMenuDetail> {
//  SELECT a.name, a.price, sum(b.menuQuantity) as "totalOrdered", sum(b.menuQuantity)*a.price as "menuCost" FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId JOIN transactions c ON c.transactionId = b.transactionId JOIN tables d ON d.tableId=c.tableId WHERE d.tableName = 'A1' AND c.status='NOT_PAID' GROUP BY a.menuId;
        val orderedMenus: ArrayList<CashierMenuDetail> = ArrayList()
        val query = "SELECT a.name, a.price, b.menuQuantity, b.menuQuantity*a.price as \"menuCost\" FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId JOIN transactions c ON c.transactionId = b.transactionId JOIN tables d ON d.tableId=c.tableId WHERE d.tableName = '$tableName' AND c.status='NOT_PAID' GROUP BY a.menuId;"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val menu = CashierMenuDetail(
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

    fun getOrderedBookData(tableName: String): ArrayList<Book> {
//SELECT a.title FROM books a JOIN detail_transactions b ON a.bookId = b.bookId JOIN transactions c ON c.transactionId = b.transactionId JOIN tables d ON d.tableId=c.tableId WHERE d.tableName = 'A1' AND c.status='NOT_PAID' GROUP BY a.bookId;
        val books: ArrayList<Book> = ArrayList()
        val query = "SELECT a.title FROM books a JOIN detail_transactions b ON a.bookId = b.bookId JOIN transactions c ON c.transactionId = b.transactionId JOIN tables d ON d.tableId=c.tableId WHERE d.tableName = '$tableName' AND c.status='NOT_PAID' GROUP BY a.bookId;"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val book = Book(
                    "",
                    rs.getString("title"),
                    "","","","",0, ItemTypeEnum.AVAILABLE
                )
                books.add(book)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return books
    }

    fun getTableCosts(tableName: String): Int {
//        SELECT 10000*if(TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)>2,TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)-1,1) as 'tableCost'FROM tables a JOIN transactions b ON a.tableId = b.tableId WHERE a.tableName = 'A1' AND b.status = 'NOT_PAID';
//        SELECT b.menuQuantity*a.price as "menuIncome" FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId JOIN transactions c ON b.transactionId = c.transactionId JOIN tables d ON d.tableId= c.tableId WHERE d.tableName = 'A1' AND c.status = 'NOT_PAID';
        var income = 0
        val querySeat = "SELECT 10000*if(TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)>$maxHours,TIMESTAMPDIFF(hour, b.checkedIn, b.checkedOut)-$maxHoursMin,1) as 'tableCost' FROM tables a JOIN transactions b ON a.tableId = b.tableId WHERE a.tableName = '$tableName' AND b.status = 'NOT_PAID';"
        val queryMenuOrdered = "SELECT b.menuQuantity*a.price as \"menuCost\" FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId JOIN transactions c ON b.transactionId = c.transactionId JOIN tables d ON d.tableId= c.tableId WHERE d.tableName = '$tableName' AND c.status = 'NOT_PAID';"
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