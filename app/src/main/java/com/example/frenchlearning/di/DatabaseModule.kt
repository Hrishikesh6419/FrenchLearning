package com.example.frenchlearning.di

import android.content.Context
import androidx.room.Room
import com.example.frenchlearning.database.ExcelDatabase
import com.example.frenchlearning.database.dao.StatementsDao
import com.example.frenchlearning.database.dao.WordsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ExcelDatabase {
        return Room.databaseBuilder(
            context,
            ExcelDatabase::class.java,
            "words_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideWordsDao(database: ExcelDatabase): WordsDao {
        return database.wordsDao()
    }

    @Provides
    fun provideStatementsDao(database: ExcelDatabase): StatementsDao {
        return database.statementDao()
    }
}
