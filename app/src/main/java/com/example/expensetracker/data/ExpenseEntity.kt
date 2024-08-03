package com.example.expensetracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "expense_table")
data class ExpenseEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    val title: String,
    val amount: Double,
    val date: Date,
    val category: String,
    val accountType:String
    )