package com.example.bookacafe.model

class Book (
    bookId: Int,
    title: String,
    author: String,
    genre: String,
    synopsis: String,
    imagePath: String,
    stock: Int,
    status: ItemTypeEnum
)