package com.example.trackinggoals.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "goals"
)
data class GoalsDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "photo")
    val photo:String,
    @ColumnInfo(name = "text_goals")
    val textGoals:String,
    @ColumnInfo(name = "data_execution")
    val dataExecution: String,
    @ColumnInfo(name = "quantity")
    val quantity:Int,
    @ColumnInfo(name = "unit")
    val unit:String,
    @ColumnInfo(name = "criterion")
    val criterion:String
) {
    fun toGoals() = Goals(
        id=id,
        photo=photo,
        textGoals=textGoals,
        dataExecution=dataExecution,
        quantity=quantity,
        unit=unit,
        criterion=criterion
    )
}