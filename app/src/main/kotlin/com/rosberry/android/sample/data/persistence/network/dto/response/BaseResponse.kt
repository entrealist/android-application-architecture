package com.rosberry.android.sample.data.persistence.network.dto.response

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("albumId") var success: Boolean = true
    @SerializedName("message") var message: String = ""
}