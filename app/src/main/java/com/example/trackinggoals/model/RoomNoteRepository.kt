package com.example.trackinggoals.model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.trackinggoals.R
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
        val dataCurrent = SimpleDateFormat("dd MMMM, yyyy").format(calendar.time)


//        val monthNames = resources.getStringArray(R.array.month)
//        как получить месяц из ресурсов

//        val monthNames = arrayOf(
//            "Январь",
//            "Февраль",
//            "Март",
//            "Апрель",
//            "Май",
//            "Июнь",
//            "Июль",
//            "Август",
//            "Сентябрь",
//            "Октябрь",
//            "Ноябрь",
//            "Декабрь"
//        )
//        val month = monthNames[calendar[Calendar.MONTH]]
//        val dataCurrent = "$month, $currentYear"
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
        val days = allDays[0].substringAfterLast(" ")
        Log.d("qqqqqq", days.toString())

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
        listNoteIncoming.removeIf { !it.note.currentData.contains(days) }


        Log.d("qqqqq", listNoteIncoming.toString())

        var list = mutableListOf<NoteIncoming>()
        for (i in 0 until allDays.size) {
            val note = Note(1, allDays[i])
            val incoming = IncomingDbEntity(1, 1, allDays[i], "", "", "").toIncoming()
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
            val incomingDbEntity = IncomingDbEntity(UUID.randomUUID().hashCode(), idNewNote, currentDataIn, "", "", "")
            noteDao.createIncoming(incomingDbEntity)
            return incomingDbEntity.toIncoming()
        } else {
            val noteDbEntity = noteDao.findByData(currentDataIn)
            val noteIdCurrent = noteDbEntity.id
            val incomingDbEntity =
                IncomingDbEntity(UUID.randomUUID().hashCode(), noteIdCurrent, currentDataIn, "", "", "")
            noteDao.createIncoming(incomingDbEntity)
            return incomingDbEntity.toIncoming()
        }
    }


    override suspend fun getCurrentDay(): Note {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.set(currentYear, currentMonth, currentDay)
        val data = SimpleDateFormat("EEEE, dd MMMM").format(calendar.time).capitalize()
        val list=noteDao.getAllNotes()
        if (list.contains(noteDao.findByData(data))){
            return noteDao.findByData(data).toNote()
        }else{
            return NoteDbEntity(1,data).toNote()
        }
    }


    override suspend fun saveNoteWithIncoming(
        incoming: Incoming
    ) {
        if (incoming.idNote==1||incoming.idIm==1){
            val idNewNote=UUID.randomUUID().hashCode()
            val noteDbEntity=NoteDbEntity(idNewNote,incoming.currentDataIn)
            noteDao.createNote(noteDbEntity)
            val incomingDbEntity=IncomingDbEntity(UUID.randomUUID().hashCode(),idNewNote,incoming.currentDataIn,incoming.textGoals,incoming.quantity,incoming.textMessages)
            noteDao.createIncoming(incomingDbEntity)
        }else{
            noteDao.createIncoming(IncomingDbEntity(incoming.idIm,incoming.idNote,incoming.currentDataIn,incoming.textGoals,incoming.quantity,incoming.textMessages))
        }
    }


    override suspend fun saveNoteWithIncomingFromGoals(progress:String,textGoals: String,note: Note){
        if (note.id==1){
            val idNewNote=UUID.randomUUID().hashCode()
            val noteDbEntity=NoteDbEntity(idNewNote,note.currentData)
            noteDao.createNote(noteDbEntity)
            val incomingDbEntity=IncomingDbEntity(UUID.randomUUID().hashCode(),idNewNote,note.currentData,textGoals,progress,"Новый результат по цели \"$textGoals\"")
            noteDao.createIncoming(incomingDbEntity)
        }else{
            noteDao.createIncoming(IncomingDbEntity(UUID.randomUUID().hashCode(),note.id,note.currentData,textGoals,progress,"Новый результат по цели \"$textGoals\""))
        }

    }

    override suspend fun updateIncoming(textMessages: String, idIm: Int) {
        noteDao.updateTextMessage(textMessages, idIm)
    }

    override suspend fun updateTextGoals(textGoals: String, idIm: Int) {
        noteDao.updateTextGoals(textGoals, idIm)
    }

    override suspend fun updateQuantity(quantity: String, idIm: Int) {
       noteDao.updateQuantity(quantity, idIm)
    }


    override suspend fun deleteIncoming(incoming: Incoming) {
        noteDao.deleteIncoming(IncomingDbEntity(incoming.idIm,incoming.idNote,incoming.currentDataIn,incoming.textGoals,incoming.quantity,incoming.textMessages))
    }

}

