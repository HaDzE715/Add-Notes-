package com.example.loginapp

import com.google.gson.annotations.SerializedName

data class NoteResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("note") val note: String
)
