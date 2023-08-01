package com.example.frenchlearning.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "statements")
data class StatementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val englishStatement: String,
    val frenchStatement: String,
    val pronunciation: String,
    val explanation: String,
    val literalTranslation: String,
    val category: String
)

