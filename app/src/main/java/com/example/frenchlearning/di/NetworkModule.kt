package com.example.frenchlearning.di

import com.example.frenchlearning.network.ExcelApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideExcelApi(): ExcelApi {
        return Retrofit.Builder()
            .baseUrl("https://docs.google.com/spreadsheets/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExcelApi::class.java)
    }
}