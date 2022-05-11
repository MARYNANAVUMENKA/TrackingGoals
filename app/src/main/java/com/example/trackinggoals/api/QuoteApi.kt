package com.example.trackinggoals.api

import com.example.trackinggoals.model.quotes.retrofit.QuoteService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QuoteApi {

    val quoteApi: QuoteService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.fisenko.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteService::class.java)
    }
}