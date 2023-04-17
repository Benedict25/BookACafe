package com.example.bookacafe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdminMemberDetails(
    var id: String,
    var name: String,
    var desc: String,
    var photo: String,
    var status: String,
): Parcelable
