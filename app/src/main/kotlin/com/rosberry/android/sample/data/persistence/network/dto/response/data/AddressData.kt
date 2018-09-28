package com.rosberry.android.sample.data.persistence.network.dto.response.data

import com.google.gson.annotations.SerializedName

class AddressData(
        @SerializedName("street") val street: String,
        @SerializedName("suite") val suite: String,
        @SerializedName("city") val city: String,
        @SerializedName("zipcode") val zipcode: String,
        @SerializedName("geo") val location: GeoData
)