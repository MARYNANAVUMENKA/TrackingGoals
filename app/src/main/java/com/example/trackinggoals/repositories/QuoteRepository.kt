package com.example.trackinggoals.repositories

import com.example.trackinggoals.model.quotes.entities.Quote

interface QuoteRepository {

    suspend fun loadQuotes(): Quote

    suspend fun loadQuotesInBackground()
}


