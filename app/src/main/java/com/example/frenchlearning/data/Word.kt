package com.example.frenchlearning.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Word(
    val englishWord: String,
    val frenchWord: String,
    val pronunciation: String,
    val relatedWords: String?,
    val explanation: String?,
    val mnemonics: String?,
    val category: Category
) : Parcelable

enum class Category {
    SIMPLE_WORDS,
    STATEMENT,
    ALPHABET,
    UNKNOWN
}
