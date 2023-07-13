package com.example.quote

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class QuoteBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_QUOTE = "com.lumeen.inside.technique.QuoteBroadcastReceiver.ACTION_QUOTE"
        const val ACTION_RESPONSE = "com.lumeen.inside.technique.QuoteBroadcastReceiver.ACTION_RESPONSE"
        const val EXTRA_QUOTE_ID = "com.lumeen.inside.technique.QuoteBroadcastReceiver.EXTRA_QUOTE_ID"
        const val EXTRA_QUOTE = "com.lumeen.inside.technique.QuoteBroadcastReceiver.EXTRA_QUOTE"
        const val EXTRA_AUTHOR = "com.lumeen.inside.technique.QuoteBroadcastReceiver.EXTRA_AUTHOR"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ACTION_QUOTE) {
            val quoteId = intent.getIntExtra(EXTRA_QUOTE_ID, -1)
            val quote = QuoteSingletonJava.getQuotes().find { it.id == quoteId }

            val responseIntent = Intent(ACTION_RESPONSE)
            responseIntent.putExtra(EXTRA_QUOTE, quote?.quote)
            responseIntent.putExtra(EXTRA_AUTHOR, quote?.author)

            context?.sendBroadcast(responseIntent)
        }
    }
}