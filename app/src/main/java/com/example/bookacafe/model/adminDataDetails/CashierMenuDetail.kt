package com.example.bookacafe.model.adminDataDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CashierMenuDetail(
    var detailTransactionId: Int,
    var menuId: String,
    var menuName: String,
    var menuPrice: String,
    var menuQuantity: String,
    var menuFinalPrice: String
): Parcelable
