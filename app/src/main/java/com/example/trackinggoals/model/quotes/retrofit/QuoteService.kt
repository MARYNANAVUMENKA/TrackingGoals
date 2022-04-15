package com.example.trackinggoals.model.quotes.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface QuoteService {

    @GET("v1/quotes/{language}/random")
    suspend fun loadQuote(
        @Path("language") language: String = Locale.getDefault().language
    ): QuoteDTO

}