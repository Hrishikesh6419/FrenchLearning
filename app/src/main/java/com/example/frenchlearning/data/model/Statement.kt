package com.example.frenchlearning.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Statement(
    val englishStatement: String,
    val frenchStatement: String,
    val pronunciation: String,
    val explanation: String,
    val literalTranslation: String,
    val category: String
) : Parcelable

