package com.example.bookacafe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CashierMenuDetails(
    var detailTransactionId: Int,
    var menuId: String,
    var menuName: String,
    var menuPrice: String,
    var menuQuantity: String,
    var menuFinalPrice: String
): Parcelable
