package com.example.trackinggoals.model.goals.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.trackinggoals.model.goals.room.entities.GoalsDbEntity

@Dao
interface GoalsDao {

    @Insert(entity = GoalsDbEntity::class)
    suspend fun createGoals(goalsDbEntity: GoalsDbEntity)

    @Delete
    suspend fun deleteGoals(goalsDbEntity: GoalsDbEntity)

    @Query("SELECT * FROM goals")
    fun getAllGoals(): List<GoalsDbEntity>

    @Query("SELECT * FROM goals WHERE id = :id")
    suspend fun findById(id: Int): GoalsDbEntity

    @Query("SELECT * FROM goals WHERE text_goals = :textGoals")
    suspend fun findByTextGoals(textGoals: String): GoalsDbEntity

    @Query("UPDATE goals SET is_active =:isActive WHERE id = :id")
    fun updateIsActive(isActive: Boolean, id: Int)

    @Query("UPDATE goals SET text_goals =:textGoals WHERE id = :id")
    fun updateText(textGoals: String, id: Int)

    @Query("UPDATE goals SET photo =:photo WHERE id = :id")
    fun updatePhoto(photo: String, id: Int)

    @Query("UPDATE goals SET data_execution =:dataExecution WHERE id = :id")
    fun updateDataExecution(dataExecution: String, id: Int)

    @Query("UPDATE goals SET progress =:progress WHERE id = :id")
    fun updateProgress(progress: Long, id: Int)

    @Query("UPDATE goals SET quantity =:quantity WHERE id = :id")
    fun updateQuantity(quantity: Long, id: Int)

    @Query("UPDATE goals SET unit =:unit WHERE id = :id")
    fun updateUnit(unit: String, id: Int)

    @Query("UPDATE goals SET criterion =:criterion WHERE id = :id")
    fun updateCriterion(criterion: String, id: Int)

}