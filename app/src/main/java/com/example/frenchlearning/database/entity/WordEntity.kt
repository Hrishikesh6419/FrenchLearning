package com.example.frenchlearning.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val englishWord: String,
    val frenchWord: String,
    val pronunciation: String,
    val relatedWords: String?,
    val explanation: String?,
    val mnemonics: String?,
    val category: String
)
