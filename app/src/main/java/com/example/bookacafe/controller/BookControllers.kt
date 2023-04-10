package com.example.bookacafe.controller

import android.database.SQLException
import com.example.bookacafe.model.Book
import com.example.bookacafe.model.ItemTypeEnum
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class BookControllers {
    private val user = ActiveUser
    var con = DatabaseHandler.connect()

    fun getBookData(genre: String, inputText: String): ArrayList<Book> {
        val books: ArrayList<Book> = ArrayList()
        val query: String
        if (inputText == "") {
            query = "SELECT * from books WHERE genre = '$genre' AND status = '${ItemTypeEnum.AVAILABLE}'"
        } else {
            query = "SELECT * from books WHERE genre = '$genre' AND status = '${ItemTypeEnum.AVAILABLE}' AND title LIKE '%$inputText%'"
        }

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val book = Book(
                    rs.getString("bookId"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),
                    rs.getString("synopsis"),
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
    fun addNewBook(book: Book): Boolean {
        return try {
            val query = "INSERT INTO books VALUES(?,?,?,?,?,?,?)"
            val stmt: PreparedStatement = con!!.prepareStatement(query)
            val bookId = createBookId()
            stmt.setString(1, bookId)
            stmt.setString(2, book.title)
            stmt.setString(3, book.author)
            stmt.setString(4, book.genre)
            stmt.setString(5, book.synopsis)
            stmt.setString(6, book.imagePath)
            stmt.setString(7, ItemTypeEnum.AVAILABLE.toString())
            stmt.executeUpdate()
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }
    fun editBook(book: Book): Boolean {
        return try {
            val query = "UPDATE books SET title = '${book.title}', author = '${book.author}', genre = '${book.genre}'," +
                    "synopsis = '${book.synopsis}', imagePath = '${book.imagePath}' WHERE bookId = '${book.bookId}'"
            val stmt: Statement = con!!.createStatement()
            stmt.executeUpdate(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }
    fun deleteBook(bookId: String): Boolean {
        return try {
            val query =
                "UPDATE books SET status = '${ItemTypeEnum.UNAVAILABLE}' WHERE bookId = '$bookId'"
            val stmt: Statement = con!!.createStatement()
            stmt.executeUpdate(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }
    fun addBookToCart(bookId: String): Boolean {
        return try {
            var cartId = ""
            var alreadyInCart = 0
            val query1 = "SELECT cartId FROM carts WHERE memberId = '${user.getId()}'"
            val stmt1: Statement = con!!.createStatement()
            val rs: ResultSet = stmt1.executeQuery(query1)
            while (rs.next()) {
                cartId = rs.getString("cartId")
            }
            val query2 = "SELECT EXISTS(SELECT * FROM detail_carts WHERE bookId = '$bookId' AND cartId = '$cartId') AS alreadyInCart"
            val rs2: ResultSet = stmt1.executeQuery(query2)
            while (rs2.next()) {
                alreadyInCart = rs2.getInt("alreadyInCart")
            }
            if (alreadyInCart != 1) {
                val query3 = "INSERT INTO detail_carts(detailCartId, cartId, bookId) VALUES(default,?,?)"
                val stmt3: PreparedStatement = con!!.prepareStatement(query3)
                stmt3.setString(1, cartId)
                stmt3.setString(2, bookId)
                stmt3.executeUpdate()
                true
            } else {
                false
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    private fun createBookId(): String {
        val query = "SELECT bookId FROM books ORDER BY bookId DESC LIMIT 1"
        var newestId = String()
        val returnId: String

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                newestId = rs.getString("bookId")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        val extractNumber = newestId.subSequence(1, 4)
        var number = extractNumber.toString().toInt()
        number += 1

        if (number < 10) {
            returnId = "B00$number"
        } else if (number < 100) {
            returnId = "B0$number"
        } else {
            returnId = "B$number"
        }

        return returnId
    }
}