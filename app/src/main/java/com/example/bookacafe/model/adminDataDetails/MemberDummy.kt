package com.example.bookacafe.model.adminDataDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemberDummy(
    var id: String,
    var name: String,
    var desc: String,
    var photo: String,
    var status: String,
): Parcelable
