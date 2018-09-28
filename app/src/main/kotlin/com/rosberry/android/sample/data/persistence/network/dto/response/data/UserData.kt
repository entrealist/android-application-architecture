package com.rosberry.android.sample.data.persistence.network.dto.response.data

import com.google.gson.annotations.SerializedName

class UserData(
        @SerializedName("name") val name: String,
        @SerializedName("id") val id: Int,
        @SerializedName("username") val username: String,
        @SerializedName("email") val email: String,
        @SerializedName("phone") val website: String,
        @SerializedName("company") val company: CompanyData,
        @SerializedName("address") val address: AddressData
)