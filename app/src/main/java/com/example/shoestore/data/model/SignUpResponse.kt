package com.example.shoestore.data.model

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("email")
    val email: String? = null
)