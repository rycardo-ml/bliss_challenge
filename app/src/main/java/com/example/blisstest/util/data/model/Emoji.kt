package com.example.blisstest.util.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emoji")
data class Emoji(

    @PrimaryKey
    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "url")
    val url: String
)