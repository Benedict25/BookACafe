package com.example.bookacafe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu (
    var menuId: String,
    var name: String,
    var price: Int,
//    var description: String,
//    var estimationTime: Integer,
//    var type: String,
    var imagePath: Int,
//    var status: ItemTypeEnum
) : Parcelable