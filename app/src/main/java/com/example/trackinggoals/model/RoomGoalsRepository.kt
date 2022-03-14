package com.example.trackinggoals.model

import kotlinx.coroutines.CoroutineDispatcher
import java.util.*

class RoomGoalsRepository(
    private val noteRepository: NoteRepository,
    private val goalsDao: GoalsDao,
    private val ioDispatcher: CoroutineDispatcher,
): GoalsRepository {

    override suspend fun getListGoals(): List<Goals> {
        val listGoalsDbEntity= goalsDao.getAllGoals()
        if(listGoalsDbEntity.isNullOrEmpty()){
            return emptyList()
        }else{
            val listGoals=listGoalsDbEntity.map {
                Goals(
                    id = it.id,
                    photo=it.photo,
                    textGoals = it.textGoals,
                    dataExecution = it.dataExecution,
                    quantity = it.quantity,
                    unit=it.unit,
                    criterion = it.criterion
                )
            }
            return listGoals
        }
    }

    override suspend fun getAllId(): List<Int> {
        return goalsDao.getAllId()
    }

    override suspend fun getIdGoals(id:Int): Goals {
        val listId=goalsDao.getAllId()

        val goals = if (listId.contains(id)) {
            goalsDao.findById(id).toGoals()
        } else {
            GoalsDbEntity(1,"","","",0,"","").toGoals()
        }
        return goals

    }

    override suspend fun createGoals() {
        val goalsDbEntity = GoalsDbEntity(UUID.randomUUID().hashCode(),"","","",0,"","" )
        goalsDao.createGoals(goalsDbEntity)
    }

    override suspend fun updateText(textGoals: String, id: Int) {
        goalsDao.updateText(textGoals, id)
    }

    override suspend fun updatePhoto(photo: String, id: Int) {
        goalsDao.updatePhoto(photo, id)
    }

    override suspend fun updateDataExecution(dataExecution: String, id: Int) {
        goalsDao.updateDataExecution(dataExecution,id)
    }


}