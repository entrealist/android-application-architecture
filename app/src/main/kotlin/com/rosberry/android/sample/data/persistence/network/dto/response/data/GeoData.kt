package com.rosberry.android.sample.data.persistence.network.dto.response.data

import com.google.gson.annotations.SerializedName

class GeoData(
        @SerializedName("lat") val lat: String,
        @SerializedName("lng") val lng: String
)