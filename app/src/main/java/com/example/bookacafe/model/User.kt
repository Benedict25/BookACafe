package com.example.bookacafe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class User(
    var userId: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String
) : Parcelable