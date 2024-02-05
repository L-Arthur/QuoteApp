package com.example.quote

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * BroadcastReceiver to handle incoming broadcasts related to quotes.
 */
class QuoteBroadcastReceiver : BroadcastReceiver() {

    companion object {
        // Action for requesting a quote
        const val ACTION_QUOTE = "com.lumeen.inside.technique.QuoteBroadcastReceiver.ACTION_QUOTE"
        // Action for sending a response containing a quote
        const val ACTION_RESPONSE = "com.lumeen.inside.technique.QuoteBroadcastReceiver.ACTION_RESPONSE"
        // Extra key for the quote ID
        const val EXTRA_QUOTE_ID = "com.lumeen.inside.technique.QuoteBroadcastReceiver.EXTRA_QUOTE_ID"
        // Extra key for the quote text
        const val EXTRA_QUOTE = "com.lumeen.inside.technique.QuoteBroadcastReceiver.EXTRA_QUOTE"
        // Extra key for the quote author
        const val EXTRA_AUTHOR = "com.lumeen.inside.technique.QuoteBroadcastReceiver.EXTRA_AUTHOR"
    }
    /**
     * Receives and handles incoming broadcast intents.
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        // Check if the received intent is for requesting a quote
        if (intent?.action == ACTION_QUOTE) {
            // Extract the quote ID from the intent
            val quoteId = intent.getIntExtra(EXTRA_QUOTE_ID, -1)
            // Find the quote with the corresponding ID
            val quote = QuoteSingletonKotlin.getQuotes().find { it.id == quoteId }

            // Create a response intent
            val responseIntent = Intent(ACTION_RESPONSE)
            // Add the quote text and author as extras to the response intent
            responseIntent.putExtra(EXTRA_QUOTE, quote?.quote)
            responseIntent.putExtra(EXTRA_AUTHOR, quote?.author)

            // Send the response broadcast
            context?.sendBroadcast(responseIntent)
        }
    }
}