package com.example.trackinggoals.screens.tab.motivation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.quotes.QuoteRepository
import kotlinx.coroutines.*

class MotivationViewModel(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _quoteText = MutableLiveData<String>()
    val quoteText: LiveData<String> = _quoteText

    private val _author = MutableLiveData<String>()
    val author: LiveData<String> = _author


    fun loadQuotes() {
        scope.launch {
            try {
                val quote = withContext(Dispatchers.IO) {
                    quoteRepository.loadQuotes()
                }
                _quoteText.value = quote.text
                _author.value = quote.author


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }
}