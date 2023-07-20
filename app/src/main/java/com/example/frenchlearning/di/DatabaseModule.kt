package com.example.frenchlearning.di

import android.content.Context
import androidx.room.Room
import com.example.frenchlearning.database.WordsDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): WordsDatabase {
        return Room.databaseBuilder(
            context,
            WordsDatabase::class.java,
            "words_database"
        ).build()
    }

    @Provides
    fun provideWordsDao(database: WordsDatabase): WordsDao {
        return database.wordsDao()
    }
}
