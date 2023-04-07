package com.example.bookacafe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Cart (
    var cartId: String,
    var table: Table,
    var books: ArrayList<Book>,
    var menus: ArrayList<Menu>,
    var menuQuantities: ArrayList<Int>
//    detailCart: ArrayList<DetailCart>
): Parcelable