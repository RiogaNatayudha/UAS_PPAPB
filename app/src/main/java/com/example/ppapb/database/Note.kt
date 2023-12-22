package com.example.ppapb.database

import android.support.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,

    @ColumnInfo(name = "judul")
    var judul: String,

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String,

    @ColumnInfo(name = "pembuatdanrating")
    var pembuatdanrating: String, // Update this field name

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "genre")
    var genre: String,

    @ColumnInfo(name = "storyline")
    var storyline: String
) {
    // Add a no-argument constructor for Firebase deserialization
    constructor() : this(0, "", "", "", "", ",", "")
}
