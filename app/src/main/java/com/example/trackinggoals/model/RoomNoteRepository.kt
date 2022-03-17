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
    ): List<NoteWithIncoming> {
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

        var listNoteWithIncoming = noteDao.getNotewithIncoming().toMutableList()
        Log.d("rrr", listNoteWithIncoming.toString())

        var list = mutableListOf<NoteWithIncoming>()
        for (i in 0 until allDays.size) {
            val noteDbEntity = NoteDbEntity(1, allDays[i])
            val incomingDbEntity = IncomingDbEntity(1, 1, "")
            val listIncomingDbEntity = listOf<IncomingDbEntity>(incomingDbEntity)
            val noteWithIncoming = NoteWithIncoming(noteDbEntity, listIncomingDbEntity)
            list.add(noteWithIncoming)
        }

        list.addAll(listNoteWithIncoming)
        for (item in listNoteWithIncoming) {
            list.removeIf { it.noteDbEntity.currentData == item.noteDbEntity.currentData && it.noteDbEntity.id == 1 }
        }
        list.sortBy { it.noteDbEntity.currentData.substringAfter(',') }

        Log.d("rrrrr", list.toString())
        return list
    }


    override suspend fun getIdNote(noteId: Int, currentData: String): Incoming {
        return when (noteId) {
            1 -> Incoming(1, 1, "")
            else -> noteDao.findByNoteId(noteId).toIncoming()
        }
    }

    override suspend fun getCurrentDay(noteId: Int): String {
        val currentDay = noteDao.findById(noteId).currentData
        return currentDay
    }

    override suspend fun getlistNoteWithIncoming(): List<NoteWithIncoming> {
        TODO("Not yet implemented")
    }

    override suspend fun saveNoteWithIncoming(text: String, noteId: Int, currentData: String) {
        var id = UUID.randomUUID().hashCode()
        val note = NoteDbEntity(id, currentData)
        noteDao.createNote(note)
        val idIm = UUID.randomUUID().hashCode()
        val incomingDbEntity = IncomingDbEntity(idIm, id, text)
        noteDao.createIncomingMessage(incomingDbEntity)
    }

    override suspend fun editNoteWithIncoming(text: String, noteId: Int, currentData: String) {
        noteDao.update(text, noteId)
    }


    override suspend fun deleteNoteWithIncoming(noteId: Int) {
        noteDao.deleteNoteWithIncoming(
            noteDao.findById(noteId),
            listOf(noteDao.findByNoteId(noteId))
        )
    }

}

