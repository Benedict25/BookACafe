package com.example.bookacafe.controller

import android.database.SQLException
import com.example.bookacafe.model.*
import java.sql.ResultSet
import java.sql.Statement

class CartControllers {
    private var con = DatabaseHandler.connect()

    fun createCartId(): String {
        val query = "SELECT cartId FROM carts ORDER BY cartId ASC"
        var newestId = String()
        var returnId = String()

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                // Bakal ke overwrite terus sampe id paling baru di DB
                newestId = rs.getString("cartId")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        // Ambil angkanya aja dari memberId terus di +1
        val extractNumber = newestId.subSequence(1, 4)
        var number = extractNumber.toString().toInt()
        number += 1

        if (number < 10) {
            returnId = "C00$number"
        } else if (number < 100) {
            returnId = "C0$number"
        } else {
            returnId = "C$number"
        }

        return returnId
    }

    fun getCartData(): Cart {
        val cartId = getCartId()
        val table = getTableFromCart()
        val books = getBookFromCart()
        val (menus, menuQuantities) = getMenuFromCart()

        val cart = Cart(cartId, table, books, menus, menuQuantities)

        return cart
    }

    private fun getCartId(): String {
        var cartId = String()

        val query = "SELECT cartId FROM carts WHERE memberId = '${ActiveUser.getId()}'"

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                cartId = rs.getString("cartId")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return cartId
    }

    fun removeTableFromCart(): Boolean {
        val query = "UPDATE carts SET tableId = NULL WHERE memberId = '${ActiveUser.getId()}'"

        return try {
            val stmt: Statement = con!!.createStatement()
            stmt.executeQuery(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    fun addMenuQuantityOnCart(menuId: String): Boolean {
        val cartId = getCartId()
        val query = "UPDATE detail_carts SET menuQuantity = menuQuantity + 1 WHERE cartId = '$cartId' AND menuId = '$menuId'"

        return try {
            val stmt: Statement = con!!.createStatement()
            stmt.executeQuery(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    fun subtractMenuQuantityOnCart(menuId: String): Boolean {
        val menuQuantity = getMenuQuantityFromCart(menuId)

        if (menuQuantity == 1) { // Quantity = 1, proceed to removal
            return removeMenuFromCart(menuId)
        } else { // Quantity > 1, proceed to normal subtraction
            val cartId = getCartId()
            val query = "UPDATE detail_carts SET menuQuantity = menuQuantity - 1 WHERE cartId = '$cartId' AND menuId = '$menuId'"

            return try {
                val stmt: Statement = con!!.createStatement()
                stmt.executeQuery(query)
                true
            } catch (e: SQLException) {
                e.printStackTrace()
                false
            }
        }
    }

    private fun getMenuQuantityFromCart(menuId: String): Int {
        val cartId = getCartId()
        val query = "SELECT d.menuQuantity\n" +
                "FROM detail_carts d\n" +
                "JOIN carts c\n" +
                "ON d.cartId = c.cartId\n" +
                "WHERE d.menuId = '$menuId' AND c.cartId = '$cartId'"
        var menuQuantity = 0

        return try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                menuQuantity = rs.getInt("menuQuantity")
            }
            menuQuantity
        } catch (e: SQLException) {
            e.printStackTrace()
            menuQuantity
        }
    }

    private fun removeMenuFromCart(menuId: String): Boolean {
        val cartId = getCartId()
        val query = "DELETE FROM detail_carts WHERE cartId = '$cartId' AND menuId = '$menuId'"

        return try {
            val stmt: Statement = con!!.createStatement()
            stmt.executeQuery(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    fun removeBookFromCart(bookId: String): Boolean {
        val cartId = getCartId()
        val query = "DELETE FROM detail_carts WHERE cartId = '$cartId' AND bookId = '$bookId'"

        return try {
            val stmt: Statement = con!!.createStatement()
            stmt.executeQuery(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    private fun getTableFromCart(): Table {
        var table = Table("", "", "", TableTypeEnum.AVAILABLE)
        val query = "SELECT t.tableId, t.tableName, t.room\n" +
                "FROM tables t\n" +
                "JOIN carts c\n" +
                "ON t.tableId = c.tableId\n" +
                "WHERE c.memberId = '${ActiveUser.getId()}'"

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                table = Table(
                    rs.getString("tableId"),
                    rs.getString("tableName"),
                    rs.getString("room"),
                    TableTypeEnum.AVAILABLE
                )
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return table
        // Cara getnya -> val(x, y) = getMenuFromCart()
    }

    private fun getMenuFromCart(): Pair<ArrayList<Menu>, ArrayList<Int>> {
        val menus: ArrayList<Menu> = ArrayList()
        val menuQuantities: ArrayList<Int> = ArrayList()
        val query = "SELECT m.menuId, m.name, m.price, m.imagePath, d.menuQuantity\n" +
                "FROM menus m\n" +
                "JOIN detail_carts d\n" +
                "ON m.menuId = d.menuId\n" +
                "JOIN carts c\n" +
                "ON d.cartId = c.cartId\n" +
                "WHERE c.memberId = '${ActiveUser.getId()}'"

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val menu = Menu(
                    rs.getString("menuId"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    "",
                    0,
                    "",
                    rs.getString("imagePath"),
                    ItemTypeEnum.AVAILABLE
                )
                val quantity = rs.getInt("menuQuantity")
                menus.add(menu)
                menuQuantities.add(quantity)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return Pair(menus, menuQuantities)
        // Cara getnya -> val(x, y) = getMenuFromCart()
    }

    private fun getBookFromCart(): ArrayList<Book> {
        val books: ArrayList<Book> = ArrayList()
        val query = "SELECT b.bookId, b.title, b.imagePath\n" +
                "FROM books b\n" +
                "JOIN detail_carts d\n" +
                "ON b.bookId = d.bookId\n" +
                "JOIN carts c\n" +
                "ON d.cartId = c.cartId\n" +
                "WHERE c.memberId = '${ActiveUser.getId()}'"

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val book = Book(
                    rs.getString("bookId"),
                    rs.getString("title"),
                    "",
                    "",
                    "",
                    rs.getString("imagePath"),
                    ItemTypeEnum.AVAILABLE
                )
                books.add(book)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return books
    }

    fun getTotalForCart(): Int {
        var total = 0
        val query = "SELECT SUM(d.menuQuantity * m.price) + 10000 as total\n" +
                "FROM menus m\n" +
                "JOIN detail_carts d\n" +
                "ON m.menuId = d.menuId\n" +
                "JOIN carts c\n" +
                "ON d.cartId = c.cartId\n" +
                "WHERE c.memberId = '${ActiveUser.getId()}'"

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                total = rs.getInt("total")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return total
    }
}