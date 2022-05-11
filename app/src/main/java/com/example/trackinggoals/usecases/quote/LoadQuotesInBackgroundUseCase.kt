package com.example.trackinggoals.usecases.quote

import com.example.trackinggoals.repositories.QuoteRepository

class LoadQuotesInBackgroundUseCase(private val repository: QuoteRepository) {
    suspend operator fun invoke()=repository.loadQuotesInBackground()
}