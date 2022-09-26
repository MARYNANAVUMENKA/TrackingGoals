package com.example.trackinggoals.domain.usecases.quote

import com.example.trackinggoals.data.repositories.QuoteRepository

class LoadQuotesInBackgroundUseCase(private val repository: QuoteRepository) {
    suspend operator fun invoke()=repository.loadQuotesInBackground()
}