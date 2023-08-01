package com.example.frenchlearning.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.frenchlearning.database.dao.WordsDao
import com.example.frenchlearning.database.entity.WordEntity

@Database(entities = [WordEntity::class], version = 1)
abstract class ExcelDatabase : RoomDatabase() {
    abstract fun wordsDao(): WordsDao
}