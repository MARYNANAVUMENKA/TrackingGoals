package com.example.trackinggoals.model

import kotlinx.coroutines.CoroutineDispatcher
import java.util.*
import javax.security.auth.PrivateCredentialPermission

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

    override suspend fun getIdGoals(id:Int): Goals {
        val listId=goalsDao.getAllId()

        val goals = if (listId.contains(id)) {
            goalsDao.findById(id).toGoals()
        } else {
            GoalsDbEntity(1,"","","",0,"","").toGoals()
        }
        return goals

    }

    override suspend fun saveGoals(
        id: Int,textGoals:String
    ) {
        val goalsDbEntity=GoalsDbEntity(UUID.randomUUID().hashCode(),"",textGoals,"",0,"","" )
        goalsDao.createGoals(goalsDbEntity)

    }

    override suspend fun editGoalsText(
        id: Int,
        textGoals: String
    ) {
        goalsDao.updateText(textGoals, id)
    }


}