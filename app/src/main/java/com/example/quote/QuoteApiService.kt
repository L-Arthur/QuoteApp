package com.example.quote

import com.google.gson.JsonObject
import retrofit2.http.GET

/**
 * Retrofit service interface for fetching quotes.
 */
interface QuoteApiService {
    /**
     * Makes a GET request to fetch quotes from the API.
     * @return JsonObject representing the response containing quotes.
     */
    @GET("quotes")
    suspend fun getQuotes(): JsonObject
}