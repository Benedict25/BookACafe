package com.example.bookacafe.model.adminDataDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CashierBookDetail (
    var detailTransactionId: Int,
    var bookId: String,
    var bookTitle: String
): Parcelable