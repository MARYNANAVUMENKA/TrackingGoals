package com.example.trackinggoals.model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineDispatcher
import java.text.SimpleDateFormat
import java.util.*

class RoomNoteRepository(
    private val noteDao: NoteDao,
    private val ioDispatcher: CoroutineDispatcher,
) : NoteRepository {


    override suspend fun getCurrentMonthYear(
        currentYear: Int,
        currentMonth: Int,
        currentDay: Int
    ): String {
        val calendar = Calendar.getInstance()
        calendar.set(currentYear, currentMonth, currentDay)

//        val monthNames: Array<String> = resources.getStringArray(R.array.month)
//        как получить месяц из ресурсов

        val monthNames = arrayOf(
            "Январь",
            "Февраль",
            "Март",
            "Апрель",
            "Май",
            "Июнь",
            "Июль",
            "Август",
            "Сентябрь",
            "Октябрь",
            "Ноябрь",
            "Декабрь"
        )
        val month = monthNames[calendar[Calendar.MONTH]]
        val dataCurrent = "$month, $currentYear"
        return dataCurrent
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun getListCurrentMonthYear(
        currentYear: Int,
        currentMonth: Int,
        currentDay: Int
    ): List<NoteIncoming> {
        val calendarCurrent = Calendar.getInstance()
        calendarCurrent.set(currentYear, currentMonth, currentDay)
        val allDays = mutableListOf<String>()
        for (i in 1..calendarCurrent.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            val calendar = Calendar.getInstance()
            calendar.set(currentYear, currentMonth, i)
            val data =
                SimpleDateFormat("EEEE, dd MMMM").format(calendar.time).capitalize()
            allDays.add(data)
        }

        var listNoteWithIncoming = noteDao.getNoteWithIncoming().toMutableList()
        var listNoteIncoming = listNoteWithIncoming.map { noteWithIncoming ->
            NoteIncoming(
                note = noteWithIncoming.noteDbEntity.toNote(),
                listIncoming = noteWithIncoming.listIncomingDbEntity.map {
                    Incoming(
                        idIm = it.idIm,
                        idNote = it.idNote,
                        currentDataIn = it.currentDataIn,
                        idGoals = it.idGoals,
                        quantity = it.quantity,
                        textMessages = it.textMessages
                    )
                }
            )
        }.toMutableList()


        Log.d("qqqqq", listNoteIncoming.toString())

        var list = mutableListOf<NoteIncoming>()
        for (i in 0 until allDays.size) {
            val note = Note(1, allDays[i])
            val incoming = IncomingDbEntity(1, 1, allDays[i], 0, "", "").toIncoming()
            val listIncoming = listOf<Incoming>(incoming)
            val noteIncoming = NoteIncoming(note, listIncoming)
            list.add(noteIncoming)
        }

        list.addAll(listNoteIncoming)
        for (item in listNoteIncoming) {
            list.removeIf { it.note.currentData == item.note.currentData && it.note.id == 1 }
        }
        list.sortBy { it.note.currentData.substringAfter(',') }

        Log.d("rrrrr", list.toString())
        return list
    }


    override suspend fun getIncoming(
        incomingId: Int,
        noteId: Int,
        currentDataIn: String
    ): Incoming {
        val listincomingDbEntity = noteDao.getAllIncoming()
        if (listincomingDbEntity.contains(noteDao.findByIncomingId(incomingId))) {
            return noteDao.findByIncomingId(incomingId).toIncoming()
        } else if (noteId == 1) {
            val idNewNote=UUID.randomUUID().hashCode()
            val noteDbEntity=NoteDbEntity(idNewNote,currentDataIn)
            noteDao.createNote(noteDbEntity)
            val incomingDbEntity = IncomingDbEntity(UUID.randomUUID().hashCode(), idNewNote, currentDataIn, 0, "", "")
            noteDao.createIncoming(incomingDbEntity)
            return incomingDbEntity.toIncoming()
        } else {
            val noteDbEntity = noteDao.findByData(currentDataIn)
            val noteIdCurrent = noteDbEntity.id
            val incomingDbEntity =
                IncomingDbEntity(UUID.randomUUID().hashCode(), noteIdCurrent, currentDataIn, 0, "", "")
            noteDao.createIncoming(incomingDbEntity)
            return incomingDbEntity.toIncoming()
        }
    }


    override suspend fun getCurrentDay(noteId: Int): String {
        val currentDay = noteDao.findById(noteId).currentData
        return currentDay
    }

    override suspend fun getlistNoteWithIncoming(): List<NoteWithIncoming> {
        TODO("Not yet implemented")
    }

    override suspend fun saveNoteWithIncoming(
        incoming: Incoming
    ) {
        if (incoming.idNote==1||incoming.idIm==1){
            val idNewNote=UUID.randomUUID().hashCode()
            val noteDbEntity=NoteDbEntity(idNewNote,incoming.currentDataIn)
            noteDao.createNote(noteDbEntity)
            val incomingDbEntity=IncomingDbEntity(UUID.randomUUID().hashCode(),idNewNote,incoming.currentDataIn,incoming.idGoals,incoming.quantity,incoming.textMessages)
            noteDao.createIncoming(incomingDbEntity)
        }else{
            noteDao.createIncoming(IncomingDbEntity(incoming.idIm,incoming.idNote,incoming.currentDataIn,incoming.idGoals,incoming.quantity,incoming.textMessages))
        }
    }

    override suspend fun updateIncoming(textMessages: String, idIm: Int) {
        noteDao.updateTextMessage(textMessages, idIm)
    }


    override suspend fun deleteIncoming(incoming: Incoming) {
        noteDao.deleteIncoming(IncomingDbEntity(incoming.idIm,incoming.idNote,incoming.currentDataIn,incoming.idGoals,incoming.quantity,incoming.textMessages))
    }

}

