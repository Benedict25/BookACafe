package com.example.bookacafe.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Book(
    var bookId: String,
    var title: String,
    var author: String,
    var genre: String,
    var synopsis: String,
    var imagePath: String,
    var status: ItemTypeEnum
) : Parcelable

