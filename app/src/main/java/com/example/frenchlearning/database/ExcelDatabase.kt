package com.example.frenchlearning.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.frenchlearning.database.dao.StatementsDao
import com.example.frenchlearning.database.dao.WordsDao
import com.example.frenchlearning.database.entity.StatementEntity
import com.example.frenchlearning.database.entity.WordEntity

@Database(entities = [WordEntity::class, StatementEntity::class], version = 3)
abstract class ExcelDatabase : RoomDatabase() {
    abstract fun wordsDao(): WordsDao
    abstract fun statementDao(): StatementsDao
}