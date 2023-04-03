package com.example.bookacafe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class DetailTransaction (
    //var detailTransactionId: Int,
    var books: String, // nnti ganti ke book!
    var bookQuantity: Int,
    var menu: String, // nnti ganti ke menu!
    var menuQuantity: Int,
    var status: DetailTransEnum
): Parcelable