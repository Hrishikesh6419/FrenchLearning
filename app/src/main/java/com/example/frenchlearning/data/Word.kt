package com.example.frenchlearning.data

data class Word(
    val englishWord: String,
    val frenchWord: String,
    val pronunciation: String,
    val relatedWords: String?,
    val explanation: String?,
    val mnemonics: String?,
    val category: Category
)

enum class Category {
    SIMPLE_WORDS,
    STATEMENT,
    ALPHABET,
    UNKNOWN
}
