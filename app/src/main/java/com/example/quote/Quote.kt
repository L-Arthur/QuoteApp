package com.example.quote

/**
 * Data class representing a single quote.
 * @property id The unique identifier of the quote.
 * @property quote The text content of the quote.
 * @property author The author of the quote.
 */
data class Quote(
    val id: Int,
    val quote: String,
    val author: String,
)

