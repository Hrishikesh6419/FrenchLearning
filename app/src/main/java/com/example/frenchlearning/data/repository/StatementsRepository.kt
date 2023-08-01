package com.example.frenchlearning.data.repository

import android.util.Log
import com.example.frenchlearning.data.model.Statement
import com.example.frenchlearning.database.dao.StatementsDao
import com.example.frenchlearning.database.entity.StatementEntity
import com.example.frenchlearning.network.ExcelApi
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import javax.inject.Inject

class StatementsRepository @Inject constructor(
    private val api: ExcelApi,
    private val statementsDao: StatementsDao
) {

    suspend fun fetchStatementsSheet(): List<Statement> {
        try {
            val statements = mutableListOf<Statement>()
            val statementEntities = mutableListOf<StatementEntity>()
            val response = api.fetchStatementSheet()

            if (response.isSuccessful) {
                val csvData: String? = response.body()?.string()
                if (csvData != null) {
                    val reader = CSVParser.parse(csvData, CSVFormat.DEFAULT.withHeader())

                    for (i in reader) {
                        val englishStatement = i.get("English Statement")
                        val frenchStatement = i.get("French Statement")
                        val pronunciation = i.get("Pronunciation")
                        val explanation = i.get("Explanation")
                        val literalTranslation = i.get("Literal Translation")
                        val category = i.get("Category")

                        val statement = Statement(
                            englishStatement,
                            frenchStatement,
                            pronunciation,
                            explanation,
                            literalTranslation,
                            category
                        )
                        statements.add(statement)

                        // Convert Word to WordEntity
                        val statementEntity = StatementEntity(
                            englishStatement = englishStatement,
                            frenchStatement = frenchStatement,
                            pronunciation = pronunciation,
                            explanation = explanation,
                            literalTranslation = literalTranslation,
                            category = category
                        )
                        statementEntities.add(statementEntity)
                    }

                    // Save all WordEntities in the database
                    statementsDao.apply {
                        nukeTable()
                        insertStatements(statementEntities)
                    }
                }
            }

            if (statements.isNotEmpty()) {
                return statements
            }
        } catch (e: Exception) {
            Log.e(
                "hrishiiii",
                "Exception in loading or parsing statements sheet: ${e.localizedMessage}"
            )
        }

        // If the API call fails or is not successful, query the Room database
        val statementsFromDatabase = statementsDao.getAllStatements().map { statementEntity ->
            Statement(
                englishStatement = statementEntity.englishStatement,
                frenchStatement = statementEntity.frenchStatement,
                pronunciation = statementEntity.pronunciation,
                explanation = statementEntity.explanation,
                literalTranslation = statementEntity.explanation,
                category = statementEntity.category
            )
        }

        return statementsFromDatabase
    }
}