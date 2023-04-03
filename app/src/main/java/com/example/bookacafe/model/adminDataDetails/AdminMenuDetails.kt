package com.example.bookacafe.model.adminDataDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class AdminMenuDetails (
    var menuPict: String,
    var menuName: String,
    var menuDesc: String
): Parcelable
