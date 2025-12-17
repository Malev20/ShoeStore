package com.example.shoestore.data.model

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("access_token")
    val access_token: String,
    @SerializedName("token_type")
    val token_type: String,
    @SerializedName("expires_in")
    val expires_in: Int,
    @SerializedName("refresh_token")
    val refresh_token: String,
    @SerializedName("user")
    val user: User
)

data class User(
    @SerializedName("id")
    val id: String,
    @SerializedName("aud")
    val aud: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_confirmed_at")
    val email_confirmed_at: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("confirmed_at")
    val confirmed_at: String? = null,
    @SerializedName("last_sign_in_at")
    val last_sign_in_at: String? = null,
    @SerializedName("app_metadata")
    val app_metadata: AppMetadata,
    @SerializedName("identities")
    val identities: List<Identity>? = null,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("updated_at")
    val updated_at: String
)

data class AppMetadata(
    @SerializedName("provider")
    val provider: String,
    @SerializedName("providers")
    val providers: List<String>
)

data class Identity(
    @SerializedName("identity_id")
    val identity_id: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("user_id")
    val user_id: String,
    @SerializedName("identity_data")
    val identity_data: IdentityData,
    @SerializedName("provider")
    val provider: String,
    @SerializedName("last_sign_in_at")
    val last_sign_in_at: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("updated_at")
    val updated_at: String
)

data class IdentityData(
    @SerializedName("email")
    val email: String,
    @SerializedName("sub")
    val sub: String
)