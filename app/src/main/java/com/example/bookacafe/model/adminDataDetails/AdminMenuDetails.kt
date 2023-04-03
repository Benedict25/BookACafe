package com.example.bookacafe.model.adminDataDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MenuDummy (
    var menuPict: String,
    var menuName: String,
    var menuDesc: String
): Parcelable
