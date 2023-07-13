package com.example.quote

import com.google.gson.JsonObject
import retrofit2.http.GET

interface QuoteApiService {
    @GET("quotes")
    suspend fun getQuotes(): JsonObject
}