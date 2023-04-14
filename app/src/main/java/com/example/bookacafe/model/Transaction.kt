package com.example.bookacafe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Time
import java.sql.Timestamp
import java.time.LocalDateTime

@Parcelize
class Transaction(
    var transactionId: String,
    var table: Table?,
    var checkedIn: Timestamp,
    var checkedOut: Timestamp?,
    var status: TransactionEnum,
    var books: ArrayList<Book>,
    var menus: ArrayList<Menu>,
    var menuQuantities: ArrayList<Int>
) : Parcelable