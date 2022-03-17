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
)


@Entity(
    tableName = "incoming"
)
data class IncomingDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_im")
    val idIm: Int,
    @ColumnInfo(name = "id_note")
    val idNote: Int,
    @ColumnInfo(name = "text_messages")
    val textMessages: String,
) {
    fun toIncoming(): Incoming =
        Incoming(
        idIm = idIm,
        idNote = idNote,
        textMessages = textMessages
    )

}


data class NoteWithIncoming(

    @Embedded val noteDbEntity: NoteDbEntity,

    @Relation(
        parentColumn = "id", entityColumn = "id_note"
    )
    val listincomingMessages: List<IncomingDbEntity>
)







