package com.example.bookacafe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp

@Parcelize
class Transaction (
    var transactionId: String,
    //var table: Table,
    var checkedIn: String, // Tar balikkin ke Timestamp!!
    //var checkedOut: Timestamp,
    //var status: TransactionEnum,
    //var detailTransaction: ArrayList<DetailTransaction>
) : Parcelable