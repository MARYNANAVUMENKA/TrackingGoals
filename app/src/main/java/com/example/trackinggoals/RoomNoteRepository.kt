package com.example.trackinggoals

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import java.text.SimpleDateFormat
import java.util.*

class RoomNoteRepository(
    private val noteDao: NoteDao,
    private val ioDispatcher: CoroutineDispatcher
) : NoteRepository {

    private val scope= CoroutineScope(ioDispatcher)

    override suspend fun getCurrentMonthYear(currentYear:Int, currentMonth:Int, currentDay:Int):String {
        val calendar = Calendar.getInstance()
        calendar.set(currentYear, currentMonth, currentDay)

        val monthNames = arrayOf(
            "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
        )
        val month = monthNames[calendar[Calendar.MONTH]]
        val dataCurrent = "$month, $currentYear"
        return dataCurrent
    }

    override suspend fun getListCurrentMonthYear(currentYear:Int, currentMonth:Int, currentDay:Int): List<Note> {

        val calendarCurrent = Calendar.getInstance()
        calendarCurrent.set(currentYear, currentMonth, currentDay)


        val allDays = mutableListOf<String>()
        for (i in 1 .. calendarCurrent.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            val calendar = Calendar.getInstance()
            calendar.set(currentYear, currentMonth, i)
            val data = SimpleDateFormat("EEEE, dd MMMM").format(calendar.time)
            allDays.add(data)
        }
        var listNotefromDb= listOf<Note>()
        for (item in allDays){
                val list= noteDao.getAllNotes().filter { it.currentData == item }
                listNotefromDb = list.map {
                    Note(
                        id=it.id,
                        currentData = it.currentData
                    )
                }
            }

        var listNote = mutableListOf<Note>()
        for (i in 0 until  allDays.size){
            if (listNotefromDb.isNotEmpty()){
                if (listNotefromDb[i]?.currentData.let { allDays.contains(it)}){
                    val note1= listNotefromDb[i]
                    listNote.add(note1)
                }
            }else{
                val note = Note(1,allDays[i])
                listNote.add(note)
            }
        }

        return listNote
    }

    override suspend fun getIdNote(noteId: Int,currentData: String): Incoming {
        when(noteId) {
            1 -> return Incoming(1, 1, "")
            else -> return noteDao.findByNoteId(noteId).toIncoming()
        }
    }

    override suspend fun getCurrentDay(noteId: Int): String {
        val currentDay = noteDao.findById(noteId).currentData
        return currentDay
        }

    override suspend fun getlistNoteWithIncoming(): List<NoteWithIncoming> {
        return if (noteDao.getNotewithIncoming().isEmpty()) {
            emptyList()
        } else {
            noteDao.getNotewithIncoming()
        }
    }

    override suspend fun saveNoteWithIncoming(text: String,noteId:Int,currentData: String) {
        if (noteId==1){
            val id=UUID.randomUUID().hashCode()
            val note=NoteDbEntity(id,currentData)
            noteDao.createNote(note)
            val idIm=UUID.randomUUID().toString().hashCode()
            val incomingDbEntity= IncomingDbEntity(idIm,id,text)
            noteDao.createIncomingMessage(incomingDbEntity)
        }else{
            val noteDbEntity=noteDao.findById(noteId)
            noteDao.deleteNote(noteDbEntity)
            val id=UUID.randomUUID().hashCode()
            val note=NoteDbEntity(id,currentData)
            noteDao.createNote(note)
            val idIm=UUID.randomUUID().toString().hashCode()
            val incomingDbEntity= IncomingDbEntity(idIm,id,text)
            noteDao.createIncomingMessage(incomingDbEntity)
        }

    }
}

