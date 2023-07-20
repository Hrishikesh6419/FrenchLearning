package com.example.frenchlearning.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.frenchlearning.database.entity.WordEntity

@Dao
interface WordsDao {
    @Query("SELECT * FROM words")
    fun getAllWords(): List<WordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<WordEntity>)
}