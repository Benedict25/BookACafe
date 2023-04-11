package com.example.bookacafe.controller

import android.util.Log
import com.example.bookacafe.model.*
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.sql.Timestamp

class TransactionControllers {

    companion object {
        var con = DatabaseHandler.connect()

        fun GetTransactionDetail(transactionId: String): Transaction{
            val transactionId = transactionId

            val table = getTableFromTransaction(transactionId)
            var status = TransactionEnum.NOT_PAID
            var (checkedIn, checkedOut, status_string) = getGeneralData(transactionId)
            val books = getBookFromTransaction(transactionId)
            val (menus, menuQuantities) = getMenuFromTransaction(transactionId)

            if (status_string == "PAID") {
                status = TransactionEnum.PAID
            } else if (status_string == "PENDING"){
                status = TransactionEnum.PENDING
            }

            val transaction: Transaction = Transaction(
                transactionId,
                table,
                Timestamp.valueOf(checkedIn),
                Timestamp.valueOf(checkedOut),
                status,
                books,
                menus,
                menuQuantities
            )
            return transaction
        }
        fun GetTransactionData(): ArrayList<Transaction> {

            val transactionIds: ArrayList<String> = getTransactionGeneralData()
            val transactions: ArrayList<Transaction> = ArrayList<Transaction>()


            for (i in transactionIds.indices) {
                val table = getTableFromTransaction(transactionIds[i])
                var status: TransactionEnum = TransactionEnum.NOT_PAID
                var (checkedIn, checkedOut, status_string) = getGeneralData(transactionIds[i])
                if (status_string == "PAID") {
//                    Log.d("TAG", "akwoakwoakwoa statusnya paid")
                    status = TransactionEnum.PAID
                } else if (status_string == "PENDING") {
                    status = TransactionEnum.PENDING
                }

                val books = getBookFromTransaction(transactionIds[i])
                val (menus, menuQuantities) = getMenuFromTransaction(transactionIds[i])

                val transaction: Transaction = Transaction(
                    transactionIds[i],
                    table,
                    Timestamp.valueOf(checkedIn),
                    Timestamp.valueOf(checkedOut),
                    status,
                    books,
                    menus,
                    menuQuantities
                )

                transactions.add(transaction)
            }

            return transactions
        }

        fun getGeneralData(transactionId: String): Triple<String, String, String> {
            val query =
                "SELECT checkedIn, checkedOut, status FROM transactions WHERE transactionId = '${transactionId}'"

            var checkedIn: String = String()
            var checkedOut: String = String()
            var status: String = String()

            try {
                val stmt: Statement = con!!.createStatement()
                val rs: ResultSet = stmt.executeQuery(query)
                while (rs.next()) {
                    checkedIn = rs.getString("checkedIn")
                    checkedOut = rs.getString("checkedOut")
                    status = rs.getString("status")
                }
            } catch (e: android.database.SQLException) {
                e.printStackTrace()
            }

            Log.d("TAG", "CheckedIn: ${checkedIn}, CheckedOut: ${checkedOut}, Status: ${status}")
            return Triple(checkedIn, checkedOut, status)
        }

        fun getMenuFromTransaction(transactionId: String): Pair<ArrayList<Menu>, ArrayList<Int>> {
            val menus: ArrayList<Menu> = ArrayList()
            val menuQuantities: ArrayList<Int> = ArrayList()

            val query = "SELECT m.menuId, m.name, m.price, d.menuQuantity " +
                    "FROM menus m " +
                    "JOIN detail_transactions d " +
                    "ON m.menuId = d.menuId " +
                    "JOIN transactions t " +
                    "ON d.transactionId = t.transactionId " +
                    "WHERE t.transactionId = '${transactionId}'"

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
                        "",
                        ItemTypeEnum.AVAILABLE
                    )
                    val quantity = rs.getInt("menuQuantity")
                    menus.add(menu)
                    menuQuantities.add(quantity)
                }
            } catch (e: android.database.SQLException) {
                e.printStackTrace()
            }

            return Pair(menus, menuQuantities)
        }

        fun getBookFromTransaction(transactionId: String): ArrayList<Book> {
            val books: ArrayList<Book> = ArrayList()
            val query = "SELECT b.bookId, b.title " +
                    "FROM books b JOIN detail_transactions d " +
                    "ON b.bookId = d.bookId " +
                    "JOIN transactions t " +
                    "ON d.transactionId = t.transactionId " +
                    "WHERE t.transactionId = '${transactionId}' "

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
                        "",
                        ItemTypeEnum.AVAILABLE
                    )
                    books.add(book)
                }
            } catch (e: android.database.SQLException) {
                e.printStackTrace()
            }

            return books
        }

        fun getTableFromTransaction(transactionId: String): Table {
            var table = Table("", "", "", TableTypeEnum.AVAILABLE)
            val query = "SELECT tables.tableId, tables.tableName, tables.room " +
                    "FROM tables JOIN transactions " +
                    "ON tables.tableId = transactions.tableId " +
                    "WHERE transactions.transactionId = '${transactionId}'"

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
            } catch (e: android.database.SQLException) {
                e.printStackTrace()
            }

            return table
        }

        fun getTransactionGeneralData(): ArrayList<String> {
            var transactionGeneralData: ArrayList<String> = ArrayList()

            val query =
                "SELECT transactionId FROM transactions WHERE memberId = '${ActiveUser.getId()}'"

            try {
                val stmt: Statement = con!!.createStatement()
                val rs: ResultSet = stmt.executeQuery(query)

                while (rs.next()) {
                    transactionGeneralData.add(rs.getString("transactionId"))
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return transactionGeneralData
        }

        fun AddTransaction() {}
        fun UpdateTransaction() {}
        fun UpdateStatusToPending(transactionId: String):Boolean {
            val query = "UPDATE transactions SET status = 'PENDING' WHERE memberId = '${transactionId}'"
            var success = false
            try {
                val stmt: Statement = con!!.createStatement()
                val rs: ResultSet = stmt.executeQuery(query)
                while (rs.next()) {
                    success = true
                }

            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return success
        }
    }
}