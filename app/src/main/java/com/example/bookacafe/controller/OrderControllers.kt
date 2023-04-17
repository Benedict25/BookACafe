package com.example.bookacafe.controller

import android.database.SQLException
import java.sql.ResultSet
import java.sql.Statement
import java.util.*

class OrderControllers {
    private var con = DatabaseHandler.connect()

    fun order(): Boolean {
        val openTransId = checkTransExistance()
        var cartIsTransfered: Boolean

        if (openTransId == "noOpenTransaction") {
            // Buat trans baru
            val newTransId = createTransId()
            val newTransIsCreated = createNewTransaction(newTransId)
            if (newTransIsCreated) {
                ActiveUser.setActiveTransaction(TransactionControllers.getTransactionDetail(newTransId))
                cartIsTransfered = transferCartToTrans(newTransId)
            } else {
                return false
            }
        } else {
            // Masukin ke trans yang lagi open punya active user
            cartIsTransfered = transferCartToTrans(openTransId)
        }

        if (cartIsTransfered) {
            val detailCartIsDeleted = deleteFromDetailCart()
            if (detailCartIsDeleted) {
                setTableStatusToBooked()
                return nullifyTableInCart()
            } else {
                return false
            }
        } else {
            return false
        }
    }

    private fun checkTransExistance(): String {
        var transactionId = "noOpenTransaction"

        val query = "SELECT transactionId\n" +
                "FROM transactions\n" +
                "WHERE memberId = '${ActiveUser.getId()}' AND status = 'NOT_PAID'\n" +
                "ORDER BY transactionId ASC"

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)

            while (rs.next()) {
                transactionId = rs.getString("transactionId")
            }

            // Gapake cek rs.first() -> transaksi pertama dibuat (new trans 1)
            // Order barang baru malah jdi transaksi baru (trans 2)
            // Tapi kalau order barang baru lg udh bner jdi msuk ke trans 2

//            if (rs.first() == false) { // No result ;  Tidak ada transaction yang open
//                return transactionId
//            }else {
//                while (rs.next()) {
//                    transactionId = rs.getString("transactionId")
//                }
//            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return transactionId
    }

    private fun createTransId(): String {
        val query = "SELECT transactionId FROM transactions ORDER BY transactionId ASC"
        var newestId = String()
        var returnId = String()

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                newestId = rs.getString("transactionId")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        // Get tanggal tudei
        val calendar = Calendar.getInstance()
        val yearNow = calendar.get(Calendar.YEAR).toInt()
        val monthNow = calendar.get(Calendar.MONTH).toInt() + 1 // +1 karena month start dari 0
        val dateNow = calendar.get(Calendar.DATE).toInt()

        // Get tanggal dari Id paling baru
        val yearFromId = newestId.subSequence(2, 6).toString().toInt()
        val monthFromId = (newestId.subSequence(6, 8).toString()).toInt() // toInt remove 0 di dpn
        val dateFromId = (newestId.subSequence(8, 10).toString()).toInt()

        // Build kode tanggal Id
        returnId += "TR$yearNow"

        if (monthNow < 10) {
            returnId += "0$monthNow"
        }else {
            returnId += "$monthNow"
        }

        if (dateNow < 10) {
            returnId += "0$dateNow"
        } else {
            returnId += "$dateNow"
        }

        // Check kode tanggal
        if (dateFromId == dateNow && monthFromId == monthNow && yearFromId == yearNow) {
            // If kode tanggal transId udh ad yg buat hari ini
            var numberId = (newestId.subSequence(11, 14).toString()).toInt()
            numberId += 1

            if (numberId < 10) {
                returnId += "-00$numberId"
            } else if (numberId < 100) {
                returnId += "-0$numberId"
            } else {
                returnId += "-$numberId"
            }
        } else {
            // Blm ad transId buat hari ini bikin yg number 001
            returnId += "-001"
        }

        return returnId
    }

    private fun createNewTransaction(transId: String): Boolean {
        val query = "INSERT INTO transactions\n" +
                "SELECT '$transId', '${ActiveUser.getId()}', c.tableId, current_timestamp(), NULL, 'NOT_PAID'\n" +
                "FROM carts c\n" +
                "WHERE c.memberId = '${ActiveUser.getId()}'"

        return try {
            val stmt: Statement = con!!.createStatement()
            stmt.executeQuery(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    private fun transferCartToTrans(transId: String): Boolean {
        val query = "INSERT INTO detail_transactions\n" +
                "SELECT NULL, '$transId', d.bookId, d.menuId, d.menuQuantity, 'NOT_SERVED'\n" +
                "FROM detail_carts d\n" +
                "JOIN carts c\n" +
                "ON d.cartId = c.cartId\n" +
                "WHERE c.memberId = '${ActiveUser.getId()}'"

        return try {
            val stmt: Statement = con!!.createStatement()
            stmt.executeQuery(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    private fun deleteFromDetailCart(): Boolean {
        val query = "DELETE FROM detail_carts\n" +
                "WHERE cartId IN (\n" +
                "\tSELECT cartId\n" +
                "\tFROM carts\n" +
                "\tWHERE memberId = '${ActiveUser.getId()}'\n" +
                ")"

        return try {
            val stmt: Statement = con!!.createStatement()
            stmt.executeQuery(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    private fun nullifyTableInCart(): Boolean {
        val query = "UPDATE carts\n" +
                "SET tableId = NULL\n" +
                "WHERE memberId = '${ActiveUser.getId()}'"

        return try {
            val stmt: Statement = con!!.createStatement()
            stmt.executeQuery(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    private fun setTableStatusToBooked() {
        val query = "UPDATE tables\n" +
                "SET status = 'BOOKED'\n" +
                "WHERE tableId \n" +
                "IN (\n" +
                "\tSELECT tableId\n" +
                "\tFROM carts\n" +
                "\tWHERE memberId = '${ActiveUser.getId()}'\n" +
                ")"

        try {
            val stmt: Statement = con!!.createStatement()
            stmt.executeQuery(query)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}