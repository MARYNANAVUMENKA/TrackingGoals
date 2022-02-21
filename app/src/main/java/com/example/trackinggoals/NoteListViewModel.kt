package com.example.trackinggoals

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class NoteListViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _listNoteLiveData = MutableLiveData<List<Note>>()
    val listNoteLiveData: LiveData<List<Note>> = _listNoteLiveData

    private val _currentMonthYearLiveData = MutableLiveData<String>()
    val currentMonthYearLiveData: LiveData<String> = _currentMonthYearLiveData

    private val _listIncomingLiveData = MutableLiveData<List<NoteWithIncoming>>()
    val listIncomingLiveData: LiveData<List<NoteWithIncoming>> = _listIncomingLiveData

    init {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        loadListNote(currentYear, currentMonth, currentDay)
        loadCurrentMonthYear(currentYear, currentMonth, currentDay)
    }

    fun loadListNote(currentYear: Int, currentMonth: Int, currentDay: Int) {
        scope.launch {
            try {
                val notes = withContext(Dispatchers.IO) {
                    noteRepository.getListCurrentMonthYear(
                        currentYear,
                        currentMonth,
                        currentDay
                    )
                }
                val listNoteWithIncoming = withContext(Dispatchers.IO) {
                    noteRepository.getlistNoteWithIncoming()
                }

                if (listNoteWithIncoming.isNotEmpty()){
                    _listIncomingLiveData.value = listNoteWithIncoming
                }
                _listIncomingLiveData.value = emptyList()
                _listNoteLiveData.value = notes
                Log.d("ttt", _listNoteLiveData.value.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadCurrentMonthYear(currentYear: Int, currentMonth: Int, currentDay: Int) {
        scope.launch {
            try {
                val currentMonthYear = withContext(Dispatchers.IO) {
                    noteRepository.getCurrentMonthYear(currentYear, currentMonth, currentDay)
                }
                _currentMonthYearLiveData.value = currentMonthYear
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}