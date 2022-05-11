package com.example.trackinggoals.screens.tab.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.notes.entities.NoteIncoming
import com.example.trackinggoals.model.notes.entities.Note
import com.example.trackinggoals.usecases.note.GetCurrentDayNoteUseCase
import com.example.trackinggoals.usecases.note.GetCurrentMonthYearNoteUseCase
import com.example.trackinggoals.usecases.note.GetListCurrentMonthYearNoteUseCase
import kotlinx.coroutines.*
import java.util.*


class NoteListViewModel(
    private val getListCurrentMonthYearNoteUseCase: GetListCurrentMonthYearNoteUseCase,
    private val getCurrentMonthYearNoteUseCase: GetCurrentMonthYearNoteUseCase,
    private val getCurrentDayNoteUseCase: GetCurrentDayNoteUseCase,

    ) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _listNoteIncomingLiveData = MutableLiveData<List<NoteIncoming>>()
    val listNoteIncomingLiveData: LiveData<List<NoteIncoming>>
        get() = _listNoteIncomingLiveData

    private val _currentMonthYearLiveData = MutableLiveData<String>()
    val currentMonthYearLiveData: LiveData<String>
        get() = _currentMonthYearLiveData

    private val _currentDayLiveData = MutableLiveData<Note>()
    val currentDayLiveData: LiveData<Note>
        get() = _currentDayLiveData

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
                    getListCurrentMonthYearNoteUseCase.invoke(currentYear, currentMonth, currentDay)
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
                    getCurrentMonthYearNoteUseCase.invoke(
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
                    getCurrentDayNoteUseCase.invoke()
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