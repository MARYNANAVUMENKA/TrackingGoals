package com.example.trackinggoals.data.repositories


import com.example.trackinggoals.data.db.NoteDao
import com.example.trackinggoals.data.entities.IncomingDbEntity
import com.example.trackinggoals.data.entities.NoteDbEntity
import com.example.trackinggoals.domain.model.Incoming
import com.example.trackinggoals.domain.model.Note
import com.example.trackinggoals.domain.model.NoteIncoming
import com.example.trackinggoals.domain.repositories.NoteRepository
import java.text.SimpleDateFormat
import java.util.*

class RoomNoteRepository(
    private val noteDao: NoteDao,
) : NoteRepository {

    override suspend fun getCurrentMonthYearNote(
        currentYear: Int,
        currentMonth: Int,
        currentDay: Int
    ): String {
        val calendar = Calendar.getInstance()
        calendar.set(currentYear, currentMonth, currentDay)
        return SimpleDateFormat("dd MMMM, yyyy").format(calendar.time)
    }


    override suspend fun getListCurrentMonthYearNote(
        currentYear: Int,
        currentMonth: Int,
        currentDay: Int
    ): List<NoteIncoming> {
        val calendarCurrent = Calendar.getInstance()
        calendarCurrent.set(currentYear, currentMonth, currentDay)
        val dataCurrentDay = SimpleDateFormat("EEEE, dd MMMM",Locale("en","ru")).format( calendarCurrent.time).replaceFirstChar { it.uppercase() }

        val allDays = mutableListOf<String>()
        for (i in 1..calendarCurrent.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            val calendar = Calendar.getInstance()
            calendar.set(currentYear, currentMonth, i)
            val data =
                SimpleDateFormat("EEEE, dd MMMM").format(calendar.time).replaceFirstChar { it.uppercase() }
            allDays.add(data)
        }
        val days = allDays[0].substringAfterLast(" ")
        var listNoteWithIncoming = noteDao.getNoteWithIncoming().toMutableList()
        var listNoteIncoming = listNoteWithIncoming.map { noteWithIncoming ->
            NoteIncoming(
                note = noteWithIncoming.noteDbEntity.toNote(),
                listIncoming = noteWithIncoming.listIncomingDbEntity.map {
                    Incoming(
                        idIm = it.idIm,
                        idNote = it.idNote,
                        currentDataIn = it.currentDataIn,
                        textGoals = it.textGoals,
                        quantity = it.quantity,
                        textMessages = it.textMessages
                    )
                }
            )
        }.toMutableList()
        listNoteIncoming.removeAll { !it.note.currentData.contains(days) }
        var list = mutableListOf<NoteIncoming>()
        for (i in 0 until allDays.size) {
            val note = Note(1, allDays[i],false)
            val incoming = IncomingDbEntity(1, 1, allDays[i], "", "", "").toIncoming()
            val listIncoming = listOf(incoming)
            val noteIncoming = NoteIncoming(note, listIncoming)
            list.add(noteIncoming)
        }
        list.addAll(listNoteIncoming)
        for (item in listNoteIncoming) {
            list.removeAll { it.note.currentData == item.note.currentData && it.note.id == 1 }
        }
        list.sortBy { it.note.currentData.substringAfter(',') }
        if (getCurrentDayNote().currentData==dataCurrentDay){
            list.firstOrNull { it.note.currentData==dataCurrentDay}?.note!!.isToday=true
        }
        return list
    }

    override suspend fun getIncoming(
        incomingId: Int,
        noteId: Int,
        currentDataIn: String
    ): Incoming {
        val listincomingDbEntity = noteDao.getAllIncoming()
        when {
            listincomingDbEntity.contains(noteDao.findByIncomingId(incomingId)) -> {
                return noteDao.findByIncomingId(incomingId).toIncoming()
            }
            noteId == ID_NEW_NOTE -> {
                val idNewNote = UUID.randomUUID().hashCode()
                val noteDbEntity = NoteDbEntity(idNewNote, currentDataIn,false)
                noteDao.createNote(noteDbEntity)
                val incomingDbEntity =
                    IncomingDbEntity(
                        UUID.randomUUID().hashCode(),
                        idNewNote,
                        currentDataIn,
                        "",
                        "",
                        ""
                    )
                noteDao.createIncoming(incomingDbEntity)
                return incomingDbEntity.toIncoming()
            }
            else -> {
                val noteDbEntity = noteDao.findByData(currentDataIn)
                val noteIdCurrent = noteDbEntity.id
                val incomingDbEntity =
                    IncomingDbEntity(
                        UUID.randomUUID().hashCode(),
                        noteIdCurrent,
                        currentDataIn,
                        "",
                        "",
                        ""
                    )
                noteDao.createIncoming(incomingDbEntity)
                return incomingDbEntity.toIncoming()
            }
        }
    }

    override suspend fun getCurrentDayNote(): Note {
        val calendar = Calendar.getInstance()
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        val data = SimpleDateFormat("EEEE, dd MMMM").format(calendar.time).replaceFirstChar { it.uppercase() }
        val list = noteDao.getAllNotes()
        return if (list.contains(noteDao.findByData(data))) {
            noteDao.findByData(data).toNote()
        } else {
            NoteDbEntity(1, data,false).toNote()
        }
    }

    override suspend fun saveNoteWithIncoming(
        incoming: Incoming
    ) {
        if (incoming.idNote == ID_NEW_NOTE || incoming.idIm == ID_NEW_INCOMING) {
            val idNewNote = UUID.randomUUID().hashCode()
            val noteDbEntity = NoteDbEntity(idNewNote, incoming.currentDataIn,false)
            noteDao.createNote(noteDbEntity)
            val incomingDbEntity = IncomingDbEntity(
                UUID.randomUUID().hashCode(),
                idNewNote,
                incoming.currentDataIn,
                incoming.textGoals,
                incoming.quantity,
                incoming.textMessages
            )
            noteDao.createIncoming(incomingDbEntity)
        } else {
            noteDao.createIncoming(
                IncomingDbEntity(
                    incoming.idIm,
                    incoming.idNote,
                    incoming.currentDataIn,
                    incoming.textGoals,
                    incoming.quantity,
                    incoming.textMessages
                )
            )
        }
    }

    override suspend fun saveNoteWithIncomingFromGoals(
        progress: String,
        textGoals: String,
        note: Note,
        text:String
    ) {

        if (note.id == ID_NEW_NOTE) {
            val idNewNote = UUID.randomUUID().hashCode()
            val noteDbEntity = NoteDbEntity(idNewNote, note.currentData,false)
            noteDao.createNote(noteDbEntity)
            val incomingDbEntity = IncomingDbEntity(
                UUID.randomUUID().hashCode(),
                idNewNote,
                note.currentData,
                textGoals,
                progress,
                "$text $textGoals"
            )
            noteDao.createIncoming(incomingDbEntity)
        } else {
            noteDao.createIncoming(
                IncomingDbEntity(
                    UUID.randomUUID().hashCode(),
                    note.id,
                    note.currentData,
                    textGoals,
                    progress,
                    "$text $textGoals"
                )
            )
        }
    }

    override suspend fun updateIncomingNote(textMessages: String, idIm: Int) {
        noteDao.updateTextMessage(textMessages, idIm)
    }

    override suspend fun updateTextGoalsNote(textGoals: String, idIm: Int) {
        noteDao.updateTextGoals(textGoals, idIm)
    }

    override suspend fun updateQuantityNote(quantity: String, idIm: Int) {
        noteDao.updateQuantity(quantity, idIm)
    }

    override suspend fun deleteIncomingNote(incoming: Incoming) {
        noteDao.deleteIncoming(
            IncomingDbEntity(
                incoming.idIm,
                incoming.idNote,
                incoming.currentDataIn,
                incoming.textGoals,
                incoming.quantity,
                incoming.textMessages
            )
        )
    }

    companion object{
        const val ID_NEW_NOTE = 1
        const val ID_NEW_INCOMING = 1
    }
}

