package com.example.frenchlearning.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Word(
    val englishWord: String,
    val frenchWord: String,
    val pronunciation: String,
    val relatedWords: String?,
    val explanation: String?,
    val category: String?
) : Parcelable
