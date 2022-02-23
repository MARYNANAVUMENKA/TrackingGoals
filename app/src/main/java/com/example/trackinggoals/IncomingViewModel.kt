package com.example.trackinggoals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IncomingViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note> = _note

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
                val textMessage = incomingCurrent.textMessages
                _textMessage.value = textMessage
                _currentData.value = currentData
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveNoteWithIncoming(text: String,noteId:Int,currentData: String) {
        scope.launch {
            try {
                withContext(Dispatchers.IO){
                    noteRepository.saveNoteWithIncoming(text,noteId,currentData)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun deleteNoteWithIncoming(text: String,noteId:Int,currentData: String){
        TODO("Not yet implemented")
    }


}

//
//
//        if (_userDetails.value != null) return
//
//        try {
//            _userDetails.value=usersService.getById(userId)
//        } catch (e: UserNotFoundException) {
//            e.printStackTrace()
//        }
//    }


