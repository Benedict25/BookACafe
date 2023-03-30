package com.example.bookacafe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class BookDummy (
    var bookPict: Int,
    var bookName: String,
    var bookDesc: String
): Parcelable
