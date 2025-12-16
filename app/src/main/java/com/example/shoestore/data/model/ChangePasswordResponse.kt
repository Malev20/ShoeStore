// data/model/ChangePasswordResponse.kt
package com.example.shoestore.data.model

data class ChangePasswordResponse(
    val id: String,
    val email: String,
    val email_confirmed_at: String?,
    val phone: String?,
    val created_at: String,
    val updated_at: String
)