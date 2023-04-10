package com.example.bookacafe.model.adminDataDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
class TableDummy (
    var tableId: String,
    var tableName: String,
    var tableDesc: String
): Parcelable