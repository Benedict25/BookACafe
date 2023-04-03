package com.example.bookacafe.controller

import android.database.SQLException
import com.example.bookacafe.model.adminDataDetails.BookDummy
import com.example.bookacafe.model.adminDataDetails.MenuDummy
import java.sql.ResultSet
import java.sql.Statement

class AdminControllers {
    var con = DatabaseHandler.connect()
    // nitip query SQL
    // query get all user: SELECT a.firstName, a.lastName, count(b.transactionId), b.status FROM members a JOIN transactions b ON a.memberId = b.memberId GROUP BY a.memberId, b.status;
    // query get all seat: SELECT a.tableName, count(b.transactionId) as "totalBooked", count(b.transactionId)*10000 as "tableIncome" FROM tables a JOIN transactions b ON a.tableId = b.tableId GROUP BY a.tableId;
    // query get all food: SELECT a.name, sum(b.menuQuantity) as "totalOrdered", sum(b.menuQuantity)*a.price as "foodIncome", a.imagePath FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId WHERE a.type = "FOOD" GROUP BY a.menuId;
    // query get all drink: SELECT a.name, sum(b.menuQuantity) as "totalOrdered", sum(b.menuQuantity)*a.price as "beverageIncome", a.imagePath FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId WHERE a.type = "BEVERAGE" GROUP BY a.menuId;
    // query get all book: SELECT a.title, sum(b.bookQuantity) as "totalOrdered", a.imagePath FROM books a JOIN detail_transactions b ON a.bookId = b.bookId GROUP BY a.bookId;
    fun disableUser(){
    }

    fun getBookData(): ArrayList<BookDummy> {
        val books: ArrayList<BookDummy> = ArrayList()
        val query = "SELECT a.title, sum(b.bookQuantity) as \"totalOrdered\", a.imagePath FROM books a JOIN detail_transactions b ON a.bookId = b.bookId GROUP BY a.bookId"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val book = BookDummy(
                    rs.getString("imagePath"),
                    rs.getString("title"),
                    "Total Ordered: " + rs.getString("totalOrdered")
                )
                books.add(book)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return books
    }

    fun getFoodData(): ArrayList<MenuDummy> {
        val foods: ArrayList<MenuDummy> = ArrayList()
        val query = "SELECT a.name, sum(b.menuQuantity) as \"totalOrdered\", sum(b.menuQuantity)*a.price as \"foodIncome\", a.imagePath FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId WHERE a.type = \"FOOD\" GROUP BY a.menuId"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val food = MenuDummy(
                    rs.getString("imagePath"),
                    rs.getString("name"),
                    "Total Ordered: " + rs.getString("totalOrdered") + "\nFood Income: Rp" + rs.getString("foodIncome")
                )
                foods.add(food)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return foods
    }

    fun getBeverageData(): ArrayList<MenuDummy> {
        val beverages: ArrayList<MenuDummy> = ArrayList()
        val query = "SELECT a.name, sum(b.menuQuantity) as \"totalOrdered\", sum(b.menuQuantity)*a.price as \"beverageIncome\", a.imagePath FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId WHERE a.type = \"BEVERAGE\" GROUP BY a.menuId"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val beverage = MenuDummy(
                    rs.getString("imagePath"),
                    rs.getString("name"),
                    "Total Ordered: " + rs.getString("totalOrdered") + "\nBeverage Income: Rp" + rs.getString("beverageIncome")
                )
                beverages.add(beverage)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return beverages
    }
    fun ShowDailyReport() {}
    fun VerifyOrder() {}
    fun VerifyPayment() {}
}