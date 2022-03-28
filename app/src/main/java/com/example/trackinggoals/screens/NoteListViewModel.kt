package com.example.trackinggoals.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.NoteIncoming
import com.example.trackinggoals.model.NoteRepository
import com.example.trackinggoals.model.NoteWithIncoming
import kotlinx.coroutines.*
import java.util.*


class NoteListViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _listNoteIncomingLiveData = MutableLiveData<List<NoteIncoming>>()
    val listNoteIncomingLiveData: LiveData<List<NoteIncoming>> = _listNoteIncomingLiveData

    private val _currentMonthYearLiveData = MutableLiveData<String>()
    val currentMonthYearLiveData: LiveData<String> = _currentMonthYearLiveData


    init {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        loadListNoteWithIncoming(currentYear, currentMonth, currentDay)
        loadCurrentMonthYear(currentYear, currentMonth, currentDay)
    }



    fun loadListNoteWithIncoming(currentYear: Int, currentMonth: Int, currentDay: Int) {
        scope.launch {
            try {
                val noteIncoming = withContext(Dispatchers.IO) {
                    noteRepository.getListCurrentMonthYear(currentYear, currentMonth, currentDay)
                }

                _listNoteIncomingLiveData.value = noteIncoming
                Log.d("ttt", _listNoteIncomingLiveData.value.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadCurrentMonthYear(currentYear: Int, currentMonth: Int, currentDay: Int) {
        scope.launch {
            try {
                val currentMonthYear = withContext(Dispatchers.IO) {
                    noteRepository.getCurrentMonthYear(
                        currentYear,
                        currentMonth,
                        currentDay
                    )
                }
                _currentMonthYearLiveData.value = currentMonthYear
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