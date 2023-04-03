package com.example.bookacafe.controller

import android.database.SQLException
import com.example.bookacafe.model.Book
import com.example.bookacafe.model.ItemTypeEnum
import com.example.bookacafe.model.Member
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class BookControllers {
    var con = DatabaseHandler.connect()

    fun getBookData(genre: String): ArrayList<Book> {
        val books: ArrayList<Book> = ArrayList()
        val query = "SELECT * from books WHERE genre = '$genre' AND status = 'available'"
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
                    rs.getInt("stock"),
                    ItemTypeEnum.AVAILABLE
                )
                books.add(book)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return books
    }
    fun addBook(book: Book): Boolean {
        return try {
            val query = "INSERT INTO books VALUES(?,?,?,?,?,?,?,?)"
            val stmt: PreparedStatement = con!!.prepareStatement(query)
            stmt.setString(1, book.bookId)
            stmt.setString(2, book.title)
            stmt.setString(3, book.author)
            stmt.setString(4, book.genre)
            stmt.setString(5, book.synopsis)
            stmt.setString(6, book.imagePath)
            stmt.setInt(7, book.stock)
            stmt.setString(8, ItemTypeEnum.AVAILABLE.toString())
            stmt.executeUpdate()
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }
    fun editBook(book: Book): Boolean {
        return try {
            val query = "UPDATE books SET title = '$book.title', author = '$book.author'," +
                    "genre = '$book.genre', synopsis = '$book.synopsis'," +
                    "imagePath = '$book.imagePath', stock = '$book.stock'" +
                    "WHERE bookId = '$book.bookId'"
            val stmt: Statement = con!!.createStatement()
            stmt.executeUpdate(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }
    fun deleteBook(book: Book): Boolean {
        return try {
            val query =
                "UPDATE books status = ${ItemTypeEnum.UNAVAILABLE} WHERE bookId = $book.bookId"
            val stmt: Statement = con!!.createStatement()
            stmt.executeUpdate(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }
    fun addBookToCart(member: Member, book: Book): Boolean {
        return try {
            val query = "INSERT INTO cart VALUES(?,?,?)"
            val stmt: PreparedStatement = con!!.prepareStatement(query)
            TODO("Bikin query")
            stmt.executeUpdate()
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }
}