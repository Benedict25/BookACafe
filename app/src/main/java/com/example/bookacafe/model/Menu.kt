package com.example.bookacafe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu (
    var menuId: String,
    var name: String,
    var price: Int,
    var description: String,
    var estimationTime: Int,
    var type: String,
    var imagePath: String,
    var status: ItemTypeEnum
) : Parcelable