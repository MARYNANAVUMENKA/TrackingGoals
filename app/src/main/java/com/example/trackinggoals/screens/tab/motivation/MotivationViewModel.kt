package com.example.trackinggoals.screens.tab.motivation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.usecases.quote.LoadQuotesUseCase
import kotlinx.coroutines.*

class MotivationViewModel(
    private val loadQuotesUseCase: LoadQuotesUseCase
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _quoteText = MutableLiveData<String>()
    val quoteText: LiveData<String>
        get() = _quoteText

    private val _author = MutableLiveData<String>()
    val author: LiveData<String>
        get() = _author


    fun loadQuotes() {
        scope.launch {
            try {
                val quote = withContext(Dispatchers.IO) {
                    loadQuotesUseCase.invoke()
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