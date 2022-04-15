package com.example.trackinggoals.model.goals.room

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.trackinggoals.model.goals.room.entities.GoalsDbEntity
import com.example.trackinggoals.model.notes.NoteRepository
import com.example.trackinggoals.model.goals.GoalsRepository
import com.example.trackinggoals.model.goals.entities.Goals
import java.util.*


class RoomGoalsRepository(
    private val noteRepository: NoteRepository,
    private val goalsDao: GoalsDao,
) : GoalsRepository {

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun getListActiveGoals(): List<Goals> {
        val listGoalsDbEntity = goalsDao.getAllGoals()
        if (listGoalsDbEntity.isNullOrEmpty()) {
            return emptyList()
        } else {
            val listGoals = listGoalsDbEntity.map {
                Goals(
                    id = it.id,
                    isActive = it.isActive,
                    photo = it.photo,
                    textGoals = it.textGoals,
                    dataExecution = it.dataExecution,
                    progress = it.progress,
                    quantity = it.quantity,
                    unit = it.unit,
                    criterion = it.criterion
                )
            }.toMutableList()
            listGoals.removeIf { it.textGoals == "" || !it.isActive }
            return listGoals
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun getListAchievedGoals(): List<Goals> {
        val listGoalsDbEntity = goalsDao.getAllGoals()
        if (listGoalsDbEntity.isNullOrEmpty()) {
            return emptyList()
        } else {
            val listGoals = listGoalsDbEntity.map {
                Goals(
                    id = it.id,
                    isActive = it.isActive,
                    photo = it.photo,
                    textGoals = it.textGoals,
                    dataExecution = it.dataExecution,
                    progress = it.progress,
                    quantity = it.quantity,
                    unit = it.unit,
                    criterion = it.criterion
                )
            }.toMutableList()
            listGoals.removeIf { it.textGoals == "" || it.isActive }
            return listGoals
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun getListGoals(): List<Goals> {
        val listGoalsDbEntity = goalsDao.getAllGoals()
        if (listGoalsDbEntity.isNullOrEmpty()) {
            return emptyList()
        } else {
            val listGoals = listGoalsDbEntity.map {
                Goals(
                    id = it.id,
                    isActive = it.isActive,
                    photo = it.photo,
                    textGoals = it.textGoals,
                    dataExecution = it.dataExecution,
                    progress = it.progress,
                    quantity = it.quantity,
                    unit = it.unit,
                    criterion = it.criterion
                )
            }.toMutableList()
            listGoals.removeIf { it.textGoals == "" }
            return listGoals
        }
    }

    override suspend fun getIdGoals(id: Int): Goals {
        return if (id == 1) {
            val id = UUID.randomUUID().hashCode()
            GoalsDbEntity(id, true, "", "", "", 0, 0, "", "").toGoals()
        } else {
            goalsDao.findById(id).toGoals()
        }
    }

    override suspend fun getTextGoals(textGoals: String): Goals {
        return goalsDao.findByTextGoals(textGoals).toGoals()
    }

    override suspend fun saveGoals(
        photo: String,
        textGoals: String,
        dataExecution: String,
        progress: Int,
        quantity: Int,
        unit: String,
        criterion: String
    ) {
        val goalsDbEntity = GoalsDbEntity(
            UUID.randomUUID().hashCode(),
            true,
            photo,
            textGoals,
            dataExecution,
            0,
            quantity,
            unit,
            criterion
        )
        goalsDao.createGoals(goalsDbEntity)
    }

    override suspend fun removeGoals(id: Int) {
        goalsDao.deleteGoals(goalsDao.findById(id))
    }

    override suspend fun updateText(textGoals: String, id: Int) {
        goalsDao.updateText(textGoals, id)
    }

    override suspend fun updatePhoto(photo: String, id: Int) {
        goalsDao.updatePhoto(photo, id)
    }

    override suspend fun updateDataExecution(dataExecution: String, id: Int) {
        goalsDao.updateDataExecution(dataExecution, id)
    }

    override suspend fun updateIsActive(isActive: Boolean, id: Int) {
        if (isActive) {
            goalsDao.updateIsActive(false, id)
        } else {
            goalsDao.updateIsActive(true, id)
        }
    }

    override suspend fun updateProgress(progress: String, id: Int, text: String) {
        val change = progress.substring(1).toInt()
        val goalsDbEntity = goalsDao.findById(id)
        val currentProgress = goalsDbEntity.progress
        val textGoalsDbEntity = goalsDbEntity.textGoals
        val note = noteRepository.getCurrentDay()
        if (progress.contains("-")) {
            goalsDao.updateProgress(currentProgress - change, id)
            noteRepository.saveNoteWithIncomingFromGoals(progress, textGoalsDbEntity, note, text)
        } else {
            goalsDao.updateProgress(currentProgress + change, id)
            noteRepository.saveNoteWithIncomingFromGoals(progress, textGoalsDbEntity, note, text)
        }
    }

    override suspend fun updateProgressWithoutNewResult(progress: String, id: Int, text: String) {
        val change = progress.substring(1).toInt()
        val goalsDbEntity = goalsDao.findById(id)
        val currentProgress = goalsDbEntity.progress
        if (progress.contains("-")) {
            goalsDao.updateProgress(currentProgress - change, id)
        } else {
            goalsDao.updateProgress(currentProgress + change, id)
        }
    }

    override suspend fun updateQuantity(quantity: Int, id: Int) {
        goalsDao.updateQuantity(quantity, id)
    }

    override suspend fun updateUnit(unit: String, id: Int) {
        goalsDao.updateUnit(unit, id)
    }

    override suspend fun updateCriterion(criterion: String, id: Int) {
        goalsDao.updateCriterion(criterion, id)
    }

}