package com.rosberry.android.sample.data.persistence.network.dto.response.data

import com.google.gson.annotations.SerializedName

class CompanyData(
        @SerializedName("name") val name: String,
        @SerializedName("catchPhrase") val catchPhrase: String,
        @SerializedName("bs") val slogan: String
)