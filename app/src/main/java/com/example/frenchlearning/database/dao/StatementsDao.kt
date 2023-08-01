package com.example.frenchlearning.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.frenchlearning.database.entity.StatementEntity

@Dao
interface StatementsDao {
    @Query("SELECT * FROM statements")
    fun getAllStatements(): List<StatementEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatements(statements: List<StatementEntity>)

    @Query("DELETE FROM statements")
    suspend fun nukeTable()
}
