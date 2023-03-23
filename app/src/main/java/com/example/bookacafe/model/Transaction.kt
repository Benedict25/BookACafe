package com.example.bookacafe.model

import java.sql.Timestamp

class Transaction (
    transactionId: String,
    table: Table,
    checkedIn: Timestamp,
    checkedOut: Timestamp,
    status: TransactionEnum,
    detailTransaction: ArrayList<DetailTransaction>
)