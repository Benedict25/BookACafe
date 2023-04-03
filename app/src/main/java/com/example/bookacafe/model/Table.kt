package com.example.bookacafe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Table (
    var tableId: String,
    var tableName: String,
    var room: String,
    var status: TableTypeEnum
): Parcelable