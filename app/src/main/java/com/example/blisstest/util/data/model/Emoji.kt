package com.example.blisstest.util.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.blisstest.list.adapter.ListItem

@Entity(tableName = "emoji")
data class Emoji(

    @PrimaryKey
    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "url")
    val url: String
): ListItem {

    override fun getDescriptionText(): String = description
    override fun getIconUrl(): String = url

}