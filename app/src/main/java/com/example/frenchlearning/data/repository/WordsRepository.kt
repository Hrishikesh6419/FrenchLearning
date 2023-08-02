package com.example.frenchlearning.data.repository

import android.util.Log
import com.example.frenchlearning.data.model.Word
import com.example.frenchlearning.database.dao.WordsDao
import com.example.frenchlearning.database.entity.WordEntity
import com.example.frenchlearning.network.ExcelApi
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import javax.inject.Inject

class WordsRepository @Inject constructor(
    private val api: ExcelApi,
    private val wordsDao: WordsDao
) {

    suspend fun fetchWordsSheet(): List<Word> {
        try {
            val words = mutableListOf<Word>()
            val wordEntities = mutableListOf<WordEntity>()
            val response = api.fetchWordsSheet()

            if (response.isSuccessful) {
                val csvData: String? = response.body()?.string()
                if (csvData != null) {
                    val reader = CSVParser.parse(csvData, CSVFormat.DEFAULT.withHeader())

                    for (i in reader) {
                        val englishWord = i.get("English Word")
                        val frenchWord = i.get("French Word")
                        val pronunciation = i.get("Pronunciation")
                        val relatedWords = i.get("Related Words")
                        val explanation = i.get("Explanation")
                        val category = i.get("Category")

                        val word = Word(
                            englishWord,
                            frenchWord,
                            pronunciation,
                            relatedWords,
                            explanation,
                            category
                        )
                        words.add(word)

                        // Convert Word to WordEntity
                        val wordEntity = WordEntity(
                            englishWord = englishWord,
                            frenchWord = frenchWord,
                            pronunciation = pronunciation,
                            relatedWords = relatedWords,
                            explanation = explanation,
                            category = category
                        )
                        wordEntities.add(wordEntity)
                    }

                    // Save all WordEntities in the database
                    wordsDao.apply {
                        nukeTable()
                        insertWords(wordEntities)
                    }
                }
            }

            if (words.isNotEmpty()) {
                return words
            }
        } catch (e: Exception) {
            Log.e("hrishiiii", "Exception in loading or parsing list: ${e.localizedMessage}")
        }

        // If the API call fails or is not successful, query the Room database
        val wordsFromDb = wordsDao.getAllWords().map { wordEntity ->
            Word(
                englishWord = wordEntity.englishWord,
                frenchWord = wordEntity.frenchWord,
                pronunciation = wordEntity.pronunciation,
                relatedWords = wordEntity.relatedWords,
                explanation = wordEntity.explanation,
                category = wordEntity.category
            )
        }

        return wordsFromDb
    }
}