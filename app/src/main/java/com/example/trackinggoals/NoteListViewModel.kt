package com.example.trackinggoals

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.util.*


class NoteListViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _listNoteWithIncomingLiveData = MutableLiveData<List<NoteWithIncoming>>()
    val listNoteWithIncomingLiveData: LiveData<List<NoteWithIncoming>> = _listNoteWithIncomingLiveData

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
                val notesWithIncoming = withContext(Dispatchers.IO) {
                    noteRepository.getListCurrentMonthYear(currentYear, currentMonth, currentDay)
                }

                _listNoteWithIncomingLiveData.value = notesWithIncoming
                Log.d("ttt", _listNoteWithIncomingLiveData.value.toString())
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
}