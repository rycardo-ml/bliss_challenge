package com.example.blisstest.util.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.blisstest.presentation.ui.common.list_items.adapter.ListItem
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(

    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    @SerializedName("login")
    @ColumnInfo(name = "user_name")
    val userName: String,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String?,

    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar")
    val avatar: String
): ListItem {

    @Transient
    @Ignore
    var fetched: Boolean = false

    override fun getDescriptionText(): String = name ?: userName
    override fun getIconUrl(): String = avatar
}