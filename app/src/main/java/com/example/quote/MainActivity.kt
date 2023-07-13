
package com.example.quote


import android.os.Bundle
import com.google.gson.JsonObject
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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

        setContent {
            MaterialTheme {
                val quotesState = remember { mutableStateListOf<Quote>() }
                val currentIndexState = remember { mutableStateOf(0) }

                Column {
                    MyButton(onClick = { fetchQuotes(quotesState, currentIndexState) })
                    DisplayQuote(quotesState, currentIndexState.value)
                }
            }
        }
    }

    //This function converts the Json Object in a Json Array and read the qoutes one by one
    private fun fetchQuotes(quotesState: MutableList<Quote>, currentIndexState: MutableState<Int>) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val gson = Gson()
                val json = apiService.getQuotes()
                val jsonArray = gson.fromJson(json, JsonObject::class.java).getAsJsonArray("quotes")

                for (i in 0 until jsonArray.size()) {
                    val quoteObject = jsonArray[i].asJsonObject
                    val quote = gson.fromJson(quoteObject, Quote::class.java)
                    quotesState.add(quote)
                    delay(1000) // Delay to show each quote for 1 second
                    currentIndexState.value = i + 1 // Increment the current index
                }

            } catch (e: Exception) {
                // Handle the error
                println("Error: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}



@Composable
fun MyButton(onClick: () -> Unit) {
    Button(onClick) {
        Text("Fetch Quotes")
    }
}

@Composable
fun DisplayQuote(quotes: List<Quote>, currentIndex: Int) {
    val quote = quotes.getOrNull(currentIndex)
    if (quote != null) {
        Column {
            Text(quote.quote)
            Text("- ${quote.author}")
        }
    }
}



