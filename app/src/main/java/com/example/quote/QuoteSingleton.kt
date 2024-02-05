package com.example.quote;

// Singleton object for managing quotes
object QuoteSingletonKotlin {
    private var quotes: List<Quote> = emptyList()

    fun getQuotes(): List<Quote> {
        return quotes
    }

    fun setQuotes(newQuotes: List<Quote>) {
        quotes = newQuotes
    }
}