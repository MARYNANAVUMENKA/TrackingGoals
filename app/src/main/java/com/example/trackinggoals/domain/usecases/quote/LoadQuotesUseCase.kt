package com.example.trackinggoals.domain.usecases.quote

import com.example.trackinggoals.domain.repositories.QuoteRepository

class LoadQuotesUseCase(private val repository: QuoteRepository) {
    suspend operator fun invoke()=repository.loadQuotes()
}