package com.example.bookacafe.controller

import android.database.SQLException
import androidx.annotation.Nullable
import com.example.bookacafe.model.*
import java.sql.ResultSet
import java.sql.Statement

class CartControllers {
    var con = DatabaseHandler.connect()

    fun GetCartData(): Cart {
        val cartId = getCartId()
        val table = getTableFromCart()
        val books = getBookFromCart()
        val (menus, menuQuantities) = getMenuFromCart()

        val cart: Cart = Cart(cartId, table, books, menus, menuQuantities)

        return cart
    }

    fun getCartId(): String {
        var cartId: String = String()

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

    fun RemoveTableFromCart(): Boolean {
        val query = "UPDATE carts SET tableId = NULL WHERE memberId = '${ActiveUser.getId()}'"

        try {
            val stmt: Statement = con!!.createStatement()
            stmt.executeQuery(query)
            return true
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        }

        return false
    }

    fun RemoveMenuFromCart(menuId: String): Boolean {
        val cartId = getCartId()
        val query = "DELETE FROM detail_carts WHERE cartId = '$cartId' AND menuId = '$menuId'"

        try {
            val stmt: Statement = con!!.createStatement()
            stmt.executeQuery(query)
            return true
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        }

        return false
    }

    fun RemoveBookFromCart(bookId: String): Boolean {
        val cartId = getCartId()
        val query = "DELETE FROM detail_carts WHERE cartId = '$cartId' AND bookId = '$bookId'"

        try {
            val stmt: Statement = con!!.createStatement()
            stmt.executeQuery(query)
            return true
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        }

        return false
    }

    fun EditMenuQuantityOnCart(menuId: String): Boolean {
        val cartId = getCartId()
        val query = "UPDATE detail_carts SET menuQuantity = menuQuantity + 1 WHERE cartId = '$cartId' AND menuId = '$menuId'"

        try {
            val stmt: Statement = con!!.createStatement()
            stmt.executeQuery(query)
            return true
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        }

        return false
    }

    fun getTableFromCart(): Table {
        var table: Table = Table("", "", "", TableTypeEnum.AVAILABLE)
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

    fun getMenuFromCart(): Pair<ArrayList<Menu>, ArrayList<Int>> {
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
                    rs.getString("imagePath")
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

    fun getBookFromCart(): ArrayList<Book> {
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
                    1,
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
        var total: Int = 0
        val query = "SELECT SUM(d.menuQuantity * m.price) as total\n" +
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