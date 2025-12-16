// data/model/ErrorResponse.kt
package com.example.shoestore.data.model

data class ErrorResponse(
    val error: String,
    val error_description: String? = null
)