package com.example.trackinggoals.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trackinggoals.domain.model.Goals

@Entity(
    tableName = "goals"
)
data class GoalsDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "is_active")
    val isActive: Boolean,
    @ColumnInfo(name = "photo")
    val photo: String,
    @ColumnInfo(name = "text_goals")
    val textGoals: String,
    @ColumnInfo(name = "data_execution")
    val dataExecution: String,
    @ColumnInfo(name = "progress")
    val progress: Long,
    @ColumnInfo(name = "quantity")
    val quantity: Long,
    @ColumnInfo(name = "unit")
    val unit: String,
    @ColumnInfo(name = "criterion")
    val criterion: String
) {
    fun toGoals() = Goals(
        id = id,
        isActive = isActive,
        photo = photo,
        textGoals = textGoals,
        dataExecution = dataExecution,
        progress = progress,
        quantity = quantity,
        unit = unit,
        criterion = criterion
    )
}