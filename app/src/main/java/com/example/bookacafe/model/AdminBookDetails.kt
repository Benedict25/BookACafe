package com.example.bookacafe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class AdminBookDetails (
    var bookPict: String,
    var bookName: String,
    var bookDesc: String
): Parcelable
