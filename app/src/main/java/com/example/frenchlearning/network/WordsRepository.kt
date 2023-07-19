package com.example.frenchlearning.network

import android.util.Log
import com.example.frenchlearning.data.Category
import com.example.frenchlearning.data.Word
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import javax.inject.Inject

class WordsRepository @Inject constructor(
    private val api: WordsApi
) {

    suspend fun fetchWords(): List<Word> {

        val response = api.fetchExcelData()


        // Convert

        if (response.isSuccessful) {
            val csvData: String? = response.body()?.string()
            if (csvData != null) {
                val reader = CSVParser.parse(csvData, CSVFormat.DEFAULT)
                for (i in reader) {
                    val englishWord = i.get(0)
                    val frenchWord = i.get(1)
                    val explanation = i.get(4)
                    Log.d(
                        "hrishiii",
                        "englishWord: $englishWord, frenchWord = $frenchWord, explanation = $explanation"
                    )
                }
            }
        }

        return listOf(
            Word(
                "Hi",
                "Bonjour",
                "Bonjour",
                "Bonjour",
                "Bonjour",
                "Bonjour",
                Category.SIMPLE_WORDS
            )
        )
    }
}