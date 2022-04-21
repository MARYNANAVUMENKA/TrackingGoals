package com.example.trackinggoals.screens.tab.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.notes.entities.NoteIncoming
import com.example.trackinggoals.model.notes.NoteRepository
import com.example.trackinggoals.model.notes.entities.Note
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

    private val _currentDayLiveData = MutableLiveData<Note>()
    val currentDayLiveData: LiveData<Note> = _currentDayLiveData

    init {
        val calendar = Calendar.getInstance()
        loadListNoteWithIncoming(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        loadCurrentMonthYear(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        loadCurrentDay()
    }

    fun loadListNoteWithIncoming(currentYear: Int, currentMonth: Int, currentDay: Int) {
        scope.launch {
            try {
                val noteIncoming = withContext(Dispatchers.IO) {
                    noteRepository.getListCurrentMonthYear(currentYear, currentMonth, currentDay)
                }

                _listNoteIncomingLiveData.value = noteIncoming
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

    fun loadCurrentDay() {
        scope.launch {
            try {
                val currentDay = withContext(Dispatchers.IO) {
                    noteRepository.getCurrentDay()
                }
                _currentDayLiveData.value = currentDay
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