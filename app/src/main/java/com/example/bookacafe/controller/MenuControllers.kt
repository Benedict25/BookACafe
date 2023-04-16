package com.example.bookacafe.controller

import android.database.SQLException
import com.example.bookacafe.model.ItemTypeEnum
import com.example.bookacafe.model.Menu
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class MenuControllers {
    private val user = ActiveUser
    private var con = DatabaseHandler.connect()

    fun getMenuData(type: String, inputText: String): ArrayList<Menu> {
        val menus: ArrayList<Menu> = ArrayList()
        val query: String
        if (inputText == "") {
            query = "SELECT * from menus WHERE type = '$type' AND status = '${ItemTypeEnum.AVAILABLE}'"
        } else {
            query = "SELECT * from menus WHERE type = '$type' AND status = '${ItemTypeEnum.AVAILABLE}' AND name LIKE '%$inputText%'"
        }

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val menu = Menu(
                    rs.getString("menuId"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("description"),
                    rs.getInt("estimationTime"),
                    rs.getString("type"),
                    rs.getString("imagePath"),
                    ItemTypeEnum.AVAILABLE
                )
                menus.add(menu)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return menus
    }
    fun addNewMenu(menu: Menu): Boolean {
        return try {
            val query = "INSERT INTO menus VALUES(?,?,?,?,?,?,?,?)"
            val stmt: PreparedStatement = con!!.prepareStatement(query)
            val menuId = createMenuId()
            stmt.setString(1, menuId)
            stmt.setString(2, menu.name)
            stmt.setInt(3, menu.price)
            stmt.setString(4, menu.description)
            stmt.setInt(5, menu.estimationTime)
            stmt.setString(6, menu.type)
            stmt.setString(7, menu.imagePath)
            stmt.setString(8, ItemTypeEnum.AVAILABLE.toString())
            stmt.executeUpdate()
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }
    fun editMenu(menu: Menu): Boolean {
        return try {
            val query = "UPDATE menus SET name = '${menu.name}', price = '${menu.price}', description = '${menu.description}'," +
                    "estimationTime = '${menu.estimationTime}', type = '${menu.type}', imagePath = '${menu.imagePath}' " +
                    "WHERE menuId = '${menu.menuId}'"
            val stmt: Statement = con!!.createStatement()
            stmt.executeUpdate(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }
    fun deleteMenu(menuId: String): Boolean {
        return try {
            val query = "UPDATE menus SET status = '${ItemTypeEnum.UNAVAILABLE}' WHERE menuId = '$menuId'"
            val stmt: Statement = con!!.createStatement()
            stmt.executeUpdate(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }
    fun addMenuToCart(menuId: String): Boolean {
        return try {
            var cartId = ""
            var cartIds = ""
            val query1 = "SELECT cartId FROM carts WHERE memberId = '${user.getId()}'"
            val stmt1: Statement = con!!.createStatement()
            val rs: ResultSet = stmt1.executeQuery(query1)

            while (rs.next()) {
                cartId = rs.getString("cartId")
            }
            cartIds = checkMenuExistence(cartId, menuId)
            if (cartIds == "-1") {
                val query2 = "INSERT INTO detail_carts(detailCartId, cartId, menuId, menuQuantity) VALUES(default,?,?,?)"
                val stmt2: PreparedStatement = con!!.prepareStatement(query2)
                stmt2.setString(1, cartId)
                stmt2.setString(2, menuId)
                stmt2.setInt(3, 1)
                stmt2.executeUpdate()
                true
            } else {
                val queryGetQuantity = "SELECT menuQuantity FROM detail_carts WHERE menuId = '${menuId}' and cartId = '${cartId}'"
                var quantity = 0
                val stmt1: Statement = con!!.createStatement()
                val rs: ResultSet = stmt1.executeQuery(queryGetQuantity)
                while (rs.next()) {
                    quantity = rs.getInt("menuQuantity") + 1
                }

                val query3 = "UPDATE detail_carts SET menuQuantity = ? WHERE menuId = ? and cartId = ?"
                val stmt2: PreparedStatement = con!!.prepareStatement(query3)
                stmt2.setInt(1, quantity)
                stmt2.setString(2, menuId)
                stmt2.setString(3, cartId)
                stmt2.executeUpdate()
                true
            }

        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }
    private fun checkMenuExistence(cartId : String, menuId : String): String {
        var cartIds = "-1"

        val query = "SELECT cartId\n" +
                "FROM detail_carts\n" +
                "WHERE menuId = '${menuId}' AND cartId = '${cartId}'\n"

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)

            while (rs.next()) {
                cartIds = rs.getString("cartId")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return cartIds
    }
    private fun createMenuId(): String {
        val query = "SELECT menuId FROM menus ORDER BY menuId DESC LIMIT 1"
        var newestId = String()
        val returnId: String

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                newestId = rs.getString("menuId")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        val extractNumber = newestId.subSequence(1, 4)
        var number = extractNumber.toString().toInt()
        number += 1

        if (number < 10) {
            returnId = "M00$number"
        } else if (number < 100) {
            returnId = "M0$number"
        } else {
            returnId = "M$number"
        }

        return returnId
    }
}