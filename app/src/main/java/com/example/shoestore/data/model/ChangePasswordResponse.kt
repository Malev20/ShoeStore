package com.example.shoestore.data.model

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("aud")
    val aud: String? = null,
    @SerializedName("role")
    val role: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("created_at")
    val created_at: String? = null,
    @SerializedName("updated_at")
    val updated_at: String? = null,
    @SerializedName("app_metadata")
    val app_metadata: Map<String, Any>? = null,
    @SerializedName("user_metadata")
    val user_metadata: Map<String, Any>? = null
)