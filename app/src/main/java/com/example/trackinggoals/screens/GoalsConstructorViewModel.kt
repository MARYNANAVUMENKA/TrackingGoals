package com.example.trackinggoals.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.Goals
import com.example.trackinggoals.model.GoalsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GoalsConstructorViewModel(
    private val goalsRepository : GoalsRepository
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)


}