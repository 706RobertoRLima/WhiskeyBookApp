package com.whiskeybook

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.annotations.SerializedName

// Retrofit and OkHttp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient

// Lifecycle & ViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class Whiskey ( @SerializedName("whiskeyName") val whiskeyName: String,
                     @SerializedName("type") val type: String?,
                     @SerializedName("country") val country: String?,
                     @SerializedName("age") val age: Int?,
                     val alcoholPercentage: Int ?= null,
                     val notes: String ?= null,
                     val imageUri: String ?= null, // Optional image path or URI*/
)
{
}
interface WhiskeyApiService {
    @GET("/whiskies/")
    suspend fun searchWhiskeys(@Query("name") name: String): List<Whiskey>
}

object WhiskeyApi  {
    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .build()
        chain.proceed(request)
    }.build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://whiskyhunter.net/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val service: WhiskeyApiService = retrofit.create(WhiskeyApiService::class.java)
}

class WhiskeyViewModel : ViewModel() {
    var whiskeyList by mutableStateOf<List<Whiskey>>(emptyList())
        private set

    fun search(query: String) {
        viewModelScope.launch {
            try {
                val result = WhiskeyApi.service.searchWhiskeys(query)
                whiskeyList = result
                Log.d("WhiskeyViewModel", "Found ${result.size} items")
            } catch (e: Exception) {
                Log.e("WhiskeyViewModel", "Error fetching data", e)
                whiskeyList = emptyList()
            }
        }
    }
}

