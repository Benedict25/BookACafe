package com.example.bookacafe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CashierBookDetails (
    var detailTransactionId: Int,
    var bookId: String,
    var bookTitle: String
): Parcelable