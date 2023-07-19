package com.example.frenchlearning.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface WordsApi {

    @GET("d/17Hp8XTSaUaOXX3DzHo-iuhS6ZP8-4k6vPB5S1z8oWY0/export?format=csv&id=17Hp8XTSaUaOXX3DzHo-iuhS6ZP8-4k6vPB5S1z8oWY0&gid=0")
    suspend fun fetchExcelData(): Response<ResponseBody>
}