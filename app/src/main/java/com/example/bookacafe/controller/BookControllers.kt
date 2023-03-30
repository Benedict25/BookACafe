package com.example.bookacafe.controller

import android.database.SQLException
import com.example.bookacafe.model.Book
import java.sql.ResultSet
import java.sql.Statement

class BookControllers {
    var con = DatabaseHandler.connect()

    fun getBookData(): ArrayList<Book> {
        val books: ArrayList<Book> = ArrayList()
        val query = "SELECT * from books"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val book = Book(
                    rs.getString("bookId"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("synopsis"),
                    0,
                    rs.getInt("stock")
                )
                books.add(book)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return books
    }
    fun AddBook() {}
    fun EditBook() {}
    fun DeleteBook() {}
    fun AddBookToCart() {}
    fun GetBookDetailsData() {}
}