
package com.example.quote


import android.os.Bundle
import com.google.gson.JsonObject
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.quote.ui.theme.QuoteTheme

// MainActivity class
class MainActivity : ComponentActivity() {

    //API calling service
    private val apiService: QuoteApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(QuoteApiService::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the content of the activity
        setContent {
            // Apply the custom theme
            QuoteTheme {
                // Define states for managing UI behavior
                val currentIndexState = remember { mutableStateOf(0) }
                // State to track if quotes are fetched or not
                val quotesFetched = remember { mutableStateOf(false) }

                // Column layout to display UI elements vertically
                Column {
                    // Show the button only if quotes are not fetched
                    if (!quotesFetched.value) {
                        MyButton(onClick = {
                            fetchQuotes(currentIndexState, quotesFetched)
                        })
                    }
                    // Show the quote only if quotes are fetched
                    if (quotesFetched.value) {
                        DisplayQuote(currentIndexState.value)
                        Button(onClick = {
                            currentIndexState.value = (currentIndexState.value + 1) % QuoteSingletonKotlin.getQuotes().size
                        }) {
                            Text("Next Quote")
                        }
                    }
                }
            }
        }
    }

    // Function to fetch quotes from the API
    private fun fetchQuotes(
        currentIndexState: MutableState<Int>,
        quotesFetched: MutableState<Boolean>
    ) {
        // Use coroutine to perform asynchronous task
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Retrieve quotes from API
                val gson = Gson()
                val json = apiService.getQuotes()
                val jsonArray =
                    gson.fromJson(json, JsonObject::class.java).getAsJsonArray("quotes")

                val quotesList = mutableListOf<Quote>()
                // Store all quotes in the List
                for (i in 0 until jsonArray.size()) {
                    val quoteObject = jsonArray[i].asJsonObject
                    val quote = gson.fromJson(quoteObject, Quote::class.java)
                    quotesList.add(quote)
                }
                // Update the quotes list in the Singleton
                QuoteSingletonKotlin.setQuotes(quotesList)
                // Update currentIndexState to trigger UI recomposition
                currentIndexState.value = 0
                // Update quotesFetched state to true
                quotesFetched.value = true


            } catch (e: Exception) {
                // Handle the error
                println("Error: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    // Button composable
    @Composable
    fun MyButton(onClick: () -> Unit) {
        Button(onClick = { onClick() }) {
            Text("Fetch Quotes")
        }
    }

    // Quote display composable
    @Composable
    fun DisplayQuote(currentIndex: Int) {
        val quote = QuoteSingletonKotlin.getQuotes()[currentIndex]
        if (quote != null) {
            Column {
                Text(text = quote.quote)
                Text(text = "- ${quote.author}")
            }
        }
    }
}



