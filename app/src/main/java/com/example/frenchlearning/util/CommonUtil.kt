package com.example.frenchlearning.util

import androidx.annotation.StringRes
import com.example.frenchlearning.R
import com.example.frenchlearning.data.model.Category

object CommonUtil {

    @StringRes
    fun getCategory(category: Category): Int {
        return when (category) {
            Category.SIMPLE_WORDS -> R.string.category_simple_words
            Category.STATEMENT -> R.string.category_statement
            Category.ALPHABET -> R.string.category_alphabet
            Category.UNKNOWN -> R.string.category_unknown
        }
    }
}