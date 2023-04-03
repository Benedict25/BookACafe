package com.example.bookacafe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Time
import java.sql.Timestamp
import java.time.LocalDateTime

@Parcelize
class Transaction(
    var transactionId: String,
    var table: String,
    var checkedIn: Timestamp, // Tar balikkin ke Timestamp!!
    var checkedOut: Timestamp,
    var status: TransactionEnum,
    var detailTransaction: ArrayList<DetailTransaction>
) : Parcelable