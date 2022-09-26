package com.example.trackinggoals.data.repositories

import com.example.trackinggoals.domain.model.Quote

interface QuoteRepository {

    suspend fun loadQuotes(): Quote

    suspend fun loadQuotesInBackground()
}


