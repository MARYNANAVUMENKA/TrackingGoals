package com.example.trackinggoals.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trackinggoals.data.entities.QuoteDbEntity

@Dao
interface QuoteDao {

    @Insert(entity = QuoteDbEntity::class)
    suspend fun saveQuote(quoteDbEntity: QuoteDbEntity)

    @Query("DELETE from quotes")
    suspend fun deleteALLQuote()

    @Query("SELECT * FROM quotes")
    fun getAllQuotes(): List<QuoteDbEntity>

    @Query("SELECT * FROM quotes WHERE current_data = :currentData")
    suspend fun findByData(currentData: String): QuoteDbEntity
}