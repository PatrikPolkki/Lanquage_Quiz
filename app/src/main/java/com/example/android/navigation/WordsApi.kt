//Patrik Pölkki
//1901921

package com.example.android.navigation

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

//base URL for the web service
private const val BASE_URL = "https://users.metropolia.fi/~patrikpo/WordQuiz/"

//moshi for converting JSON data to kotlin object
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

//retrofit builder which contains moshi and base_url
private val retro = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

//specify path to do getWords
interface WordsApiService {
    @GET("words.json")
    suspend fun getWords(): Set<Word>
}

//wordsApi object that implements WordsApiServices
object WordsApi {
    val retrofitService: WordsApiService by lazy {
        retro.create(WordsApiService::class.java)
    }
}