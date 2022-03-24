package com.example.trackinggoals.model

import androidx.room.*

@Entity(
    tableName = "notes",
    indices = [Index("current_data", unique = true)]
)

data class NoteDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "current_data")
    val currentData: String,
) {
    fun toNote(): Note =
        Note(
            id = id,
            currentData = currentData
        )
}


@Entity(
    tableName = "incoming"
)
data class IncomingDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_im")
    val idIm: Int,
    @ColumnInfo(name = "id_note")
    val idNote: Int,
    @ColumnInfo(name = "current_data_in")
    val currentDataIn: String,
    @ColumnInfo(name = "id_goals")
    val idGoals:Int,
    @ColumnInfo(name = "quantity")
    val quantity:String,
    @ColumnInfo(name = "text_messages")
    val textMessages: String,
) {
    fun toIncoming(): Incoming =
        Incoming(
            idIm = idIm,
            idNote = idNote,
            currentDataIn=currentDataIn,
            idGoals=idGoals,
            quantity=quantity,
            textMessages = textMessages
        )
}


data class NoteWithIncoming(

    @Embedded val noteDbEntity: NoteDbEntity,

    @Relation(
        parentColumn = "id", entityColumn = "id_note"
    )
    val listIncomingDbEntity: List<IncomingDbEntity>
)







