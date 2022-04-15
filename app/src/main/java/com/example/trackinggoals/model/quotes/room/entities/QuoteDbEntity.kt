package com.example.trackinggoals.model.quotes.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trackinggoals.model.quotes.entities.Quote

@Entity(
    tableName = "quotes"
)

data class QuoteDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "current_data")
    val currentData: String,
) {
    fun toQuotes() = Quote(
        id = id,
        text = text,
        author = author,
        currentData = currentData
    )
}