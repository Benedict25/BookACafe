package com.example.bookacafe.controller

import android.util.Log
import com.example.bookacafe.model.*
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.sql.Timestamp

class TransactionControllers {

    companion object {
        private var con = DatabaseHandler.connect()

        fun getTransactionDetail(transactionId: String): Transaction {
            val transactionId = transactionId

            var timestamp: Timestamp?

            val table = getTableFromTransaction(transactionId)
            var status = TransactionEnum.NOT_PAID
            var (checkedIn, checkedOut, status_string) = getGeneralData(transactionId)
            val books = getBookFromTransaction(transactionId)
            val (menus, menuQuantities) = getMenuFromTransaction(transactionId)

            if (status_string == "PAID") {
                status = TransactionEnum.PAID
            } else if (status_string == "PENDING") {
                status = TransactionEnum.PENDING
            } else if (status_string == "CANCELLED") {
                status = TransactionEnum.CANCELLED
            }

            if (checkedOut == "null") {
                timestamp = null
            } else {
                timestamp = Timestamp.valueOf(checkedOut)
            }

            val transaction: Transaction = Transaction(
                transactionId,
                table,
                Timestamp.valueOf(checkedIn),
                timestamp,
                status,
                books,
                menus,
                menuQuantities
            )
            return transaction
        }

        fun getTransactionData(): ArrayList<Transaction> {
            val transactionIds: ArrayList<String> = getTransactionGeneralData()
            val transactions: ArrayList<Transaction> = ArrayList()
            var timestamp: Timestamp?

            for (i in transactionIds.indices) {
                val table = getTableFromTransaction(transactionIds[i])
                var status: TransactionEnum = TransactionEnum.NOT_PAID
                var (checkedIn, checkedOut, status_string) = getGeneralData(transactionIds[i])
                if (status_string == "PAID") {
                    status = TransactionEnum.PAID
                } else if (status_string == "PENDING") {
                    status = TransactionEnum.PENDING
                } else if (status_string == "CANCELLED") {
                    status = TransactionEnum.CANCELLED
                }

                if (checkedOut == "null") {
                    timestamp = null
                } else {
                    timestamp = Timestamp.valueOf(checkedOut)
                }

                val books = getBookFromTransaction(transactionIds[i])
                val (menus, menuQuantities) = getMenuFromTransaction(transactionIds[i])

                val transaction = Transaction(
                    transactionIds[i],
                    table,
                    Timestamp.valueOf(checkedIn),
                    timestamp,
                    status,
                    books,
                    menus,
                    menuQuantities
                )

                transactions.add(transaction)
            }

            return transactions
        }

        private fun getGeneralData(transactionId: String): Triple<String, String, String> {
            val query =
                "SELECT checkedIn, checkedOut, status FROM transactions WHERE transactionId = '${transactionId}'"

            var checkedIn = String()
            var checkedOut = String()
            var status = String()

            try {
                val stmt: Statement = con!!.createStatement()
                val rs: ResultSet = stmt.executeQuery(query)
                while (rs.next()) {
                    checkedIn = rs.getString("checkedIn")
                    if (rs.getString("checkedOut") == null) {// if you fetched null value then initialize output with blank string
                        checkedOut = "null"
                    } else {
                        checkedOut = rs.getString("checkedOut")
                    }
                    status = rs.getString("status")
                }
            } catch (e: android.database.SQLException) {
                e.printStackTrace()
            }

            Log.d("TAG", "CheckedIn: $checkedIn, CheckedOut: $checkedOut, Status: $status")
            return Triple(checkedIn, checkedOut, status)
        }

        private fun getMenuFromTransaction(transactionId: String): Pair<ArrayList<Menu>, ArrayList<Int>> {
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

        private fun getBookFromTransaction(transactionId: String): ArrayList<Book> {
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

        private fun getTableFromTransaction(transactionId: String): Table {
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

        private fun getTransactionGeneralData(): ArrayList<String> {
            var transactionGeneralData: ArrayList<String> = ArrayList()

            val query =
                "SELECT transactionId FROM transactions WHERE memberId = '${ActiveUser.getId()}' ORDER BY checkedIn DESC"

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

        fun addTransaction() {}
        fun updateTransaction() {}
        fun updateStatusToPending(transactionId: String): Boolean {
            val query =
                "UPDATE transactions SET status = 'PENDING', checkedOut = current_timestamp() WHERE transactionId = '${transactionId}'"
            var success = false
            try {
                val stmt = con!!.prepareStatement(query)
                stmt.execute()
                success = true

            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return success
        }
    }
}