package com.example.trackinggoals.model

import kotlinx.coroutines.CoroutineDispatcher
import java.util.*



class RoomGoalsRepository(
    private val noteRepository: NoteRepository,
    private val goalsDao: GoalsDao,
    private val ioDispatcher: CoroutineDispatcher,
) : GoalsRepository {


    override suspend fun getListGoals(): List<Goals> {
        val listGoalsDbEntity = goalsDao.getAllGoals()
        if (listGoalsDbEntity.isNullOrEmpty()) {
            return emptyList()
        } else {
            val listGoals = listGoalsDbEntity.map {
                Goals(
                    id = it.id,
                    isActive = true,
                    photo = it.photo,
                    textGoals = it.textGoals,
                    dataExecution = it.dataExecution,
                    progress=it.progress,
                    quantity = it.quantity,
                    unit = it.unit,
                    criterion = it.criterion
                )
            }
            return listGoals
        }
    }


    override suspend fun getIdGoals(id: Int): Goals {
        return goalsDao.findById(id).toGoals()
    }

    override suspend fun getTextGoals(textGoals: String): Goals {
        return goalsDao.findByTextGoals(textGoals).toGoals()
    }

    override suspend fun createGoals(): Int {
        val id = UUID.randomUUID().hashCode()
        val goalsDbEntity = GoalsDbEntity(id, true, "", "", "", 0, 0,"", "" )
        goalsDao.createGoals(goalsDbEntity)
        return id
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
        if (isActive){
            goalsDao.updateIsActive(false,id)
        }else{
            goalsDao.updateIsActive(true,id)
        }

    }

    override suspend fun updateProgress(progress: String, id: Int) {
        val change = progress.substring(1).toInt()
        val goalsDbEntity=goalsDao.findById(id)
        val currentProgress=goalsDbEntity.progress
        val textGoalsDbEntity=goalsDbEntity.textGoals

        val note = noteRepository.getCurrentDay()

        if (progress.contains("-")){
            goalsDao.updateProgress(currentProgress-change, id)

            noteRepository.saveNoteWithIncomingFromGoals(progress,textGoalsDbEntity,note)
        }else{
            goalsDao.updateProgress(currentProgress+change, id)
            noteRepository.saveNoteWithIncomingFromGoals(progress,textGoalsDbEntity,note)
        }

    }

    override suspend fun updateProgressWithoutIncoming(progress: String, id: Int) {
        val change = progress.substring(1).toInt()
        val goalsDbEntity=goalsDao.findById(id)
        val currentProgress=goalsDbEntity.progress
        val textGoalsDbEntity=goalsDbEntity.textGoals

        val note = noteRepository.getCurrentDay()

        if (progress.contains("-")){
            goalsDao.updateProgress(currentProgress-change, id)

        }else{
            goalsDao.updateProgress(currentProgress+change, id)

        }
    }

    override suspend fun updateQuantity(quantity: Int, id: Int) {
        goalsDao.updateQuantity(quantity,id)
    }

    override suspend fun updateUnit(unit: String, id: Int) {
        goalsDao.updateUnit(unit, id)
    }

    override suspend fun updateCriterion(criterion: String, id: Int) {
        goalsDao.updateCriterion(criterion, id)
    }





}