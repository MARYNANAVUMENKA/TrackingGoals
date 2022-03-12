package com.example.trackinggoals.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.Note
import com.example.trackinggoals.model.NoteRepository
import kotlinx.coroutines.*

class IncomingViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _textMessage = MutableLiveData<String>()
    val textMessage: LiveData<String> = _textMessage

    private val _currentData = MutableLiveData<String>()
    val currentData: LiveData<String> = _currentData

    fun loadNoteWithIncoming(idNote: Int, currentData: String) {
        scope.launch {
            try {
                val incomingCurrent = withContext(Dispatchers.IO) {
                    noteRepository.getIdNote(idNote, currentData)
                }
                _textMessage.value = incomingCurrent.textMessages
                _currentData.value = currentData
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveNoteWithIncoming(text: String, noteId: Int, currentData: String) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    noteRepository.saveNoteWithIncoming(text, noteId, currentData)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun editNoteWithIncoming(text: String, noteId: Int, currentData: String) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    noteRepository.editNoteWithIncoming(text, noteId, currentData)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteNoteWithIncoming(noteId: Int) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    noteRepository.deleteNoteWithIncoming(noteId)
                }
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



