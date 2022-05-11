package com.example.trackinggoals.usecases.quote

import com.example.trackinggoals.repositories.QuoteRepository

class LoadQuotesUseCase(private val repository: QuoteRepository) {
    suspend operator fun invoke()=repository.loadQuotes()
}