package com.example.trackinggoals.model.quotes.room

import com.example.trackinggoals.model.quotes.entities.Quote
import com.example.trackinggoals.model.quotes.room.entities.QuoteDbEntity
import com.example.trackinggoals.model.quotes.retrofit.QuoteService
import com.example.trackinggoals.repositories.QuoteRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class RoomQuoteRepository(
    private val api: QuoteService,
    private val quoteDao: QuoteDao
) : QuoteRepository {

    override suspend fun loadQuotes(): Quote {
        val calendar = Calendar.getInstance()
        calendar.set(
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(
                Calendar.DAY_OF_MONTH
            )
        )
        val data = SimpleDateFormat("EEEE, dd MMMM").format(calendar.time)
        val list = quoteDao.getAllQuotes()
        return if (list.contains(quoteDao.findByData(data))) {
            quoteDao.findByData(data).toQuotes()
        } else {
            return try {
                val quote = api.loadQuote()
                val quoteDbEntity =
                    QuoteDbEntity(quote.id!!, quote.text!!, quote.author!!.name!!, data)
                quoteDao.saveQuote(quoteDbEntity)
                quoteDbEntity.toQuotes()
            } catch (e: Exception) {
                e.printStackTrace()
                if (list.isNotEmpty()) {
                    list[list.lastIndex].toQuotes()
                } else {
                    Quote("", "", "", "")
                }
            }
        }
    }

    override suspend fun loadQuotesInBackground() {
        val calendar = Calendar.getInstance()
        calendar.set(
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(
                Calendar.DAY_OF_MONTH
            )
        )
        val data = SimpleDateFormat("EEEE, dd MMMM").format(calendar.time)
        val list = quoteDao.getAllQuotes()
        if (!list.contains(quoteDao.findByData(data))) {
            try {
                val quote = api.loadQuote()
                val quoteDbEntity =
                    QuoteDbEntity(quote.id!!, quote.text!!, quote.author!!.name!!, data)
                quoteDao.saveQuote(quoteDbEntity)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}