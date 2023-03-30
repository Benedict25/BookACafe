package com.example.bookacafe.model.adminDummy

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemberDummy(
    var name: String,
    var desc: String,
    var photo: String
): Parcelable
