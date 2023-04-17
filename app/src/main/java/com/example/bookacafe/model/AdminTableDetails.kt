package com.example.bookacafe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
class AdminTableDetails (
    var tableId: String,
    var tableName: String,
    var tableDesc: String
): Parcelable