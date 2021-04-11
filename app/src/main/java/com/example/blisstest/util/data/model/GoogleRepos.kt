package com.example.blisstest.util.data.model

import com.google.gson.annotations.SerializedName

data class GoogleRepos(

    @SerializedName("id")
    val id: Long,

    @SerializedName("full_name")
    val name: String,

    @SerializedName("private")
    val private: Boolean
)