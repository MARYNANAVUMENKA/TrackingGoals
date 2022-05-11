package com.example.trackinggoals.model.goals.room


import com.example.trackinggoals.model.goals.room.entities.GoalsDbEntity
import com.example.trackinggoals.repositories.GoalsRepository
import com.example.trackinggoals.model.goals.entities.Goals
import java.util.*


class RoomGoalsRepository(
    private val goalsDao: GoalsDao,
) : GoalsRepository {

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
            listGoals.removeAll { !it.isActive }
            return listGoals
        }
    }


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
            listGoals.removeAll { it.isActive }
            return listGoals
        }
    }


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
            return listGoals
        }
    }

    override suspend fun getIdGoals(id: Int): Goals {
        return if (id == ID_NEW_GOALS) {
            val id = UUID.randomUUID().hashCode()
            GoalsDbEntity(id, true, "", "", "", 0L, 0L, "", "").toGoals()
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
        progress: Long,
        quantity: Long,
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

    override suspend fun updateTextGoals(textGoals: String, id: Int) {
        goalsDao.updateText(textGoals, id)
    }

    override suspend fun updatePhotoGoals(photo: String, id: Int) {
        goalsDao.updatePhoto(photo, id)
    }

    override suspend fun updateDataExecutionGoals(dataExecution: String, id: Int) {
        goalsDao.updateDataExecution(dataExecution, id)
    }

    override suspend fun updateIsActiveGoals(isActive: Boolean, id: Int) {
        if (isActive) {
            goalsDao.updateIsActive(false, id)
        } else {
            goalsDao.updateIsActive(true, id)
        }
    }

    override suspend fun updateProgressGoals(progress: String, id: Int, text: String) {
        val change = progress.substring(1).toLong()
        val goalsDbEntity = goalsDao.findById(id)
        val currentProgress = goalsDbEntity.progress
        val quantity = goalsDbEntity.quantity
        val changeMinusProgress = currentProgress - change
        val changePlusProgress = currentProgress + change
        if (progress.contains("-")) {
            when (changeMinusProgress) {
                in 1L..quantity -> goalsDao.updateProgress(changeMinusProgress, id)
                else -> goalsDao.updateProgress(0L, id)
            }
        } else if (progress.contains("+")) {
            if (changePlusProgress > quantity) {
                goalsDao.updateProgress(quantity, id)
            } else {
                goalsDao.updateProgress(currentProgress + change, id)
            }
        }
    }

    override suspend fun updateProgressWithoutNewResultGoals(
        progress: String,
        id: Int,
        text: String
    ) {
        updateProgressGoals(progress, id, text)
    }

    override suspend fun updateQuantityGoals(quantity: Long, id: Int) {
        goalsDao.updateQuantity(quantity, id)
    }

    override suspend fun updateUnitGoals(unit: String, id: Int) {
        goalsDao.updateUnit(unit, id)
    }

    override suspend fun updateCriterionGoals(criterion: String, id: Int) {
        goalsDao.updateCriterion(criterion, id)
    }


    override suspend fun getAllActiveGoalsForIncoming(): ArrayList<String> {
        val listGoals = getListActiveGoals()
        return if (listGoals.isNotEmpty()) {
            listGoals.map { goals: Goals -> goals.textGoals } as ArrayList<String>
        } else {
            emptyList<String>() as ArrayList<String>
        }
    }

    companion object {
        const val ID_NEW_GOALS = 1
    }

}