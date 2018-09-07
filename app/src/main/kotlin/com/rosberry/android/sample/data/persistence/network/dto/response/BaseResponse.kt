package com.rosberry.android.sample.data.persistence.network.dto.response

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("success") var success: Boolean = true
    @SerializedName("error") var error: ErrorData? = null

    class ErrorData{
        @SerializedName("code") var code: String = "error"
        @SerializedName("message") var message: String = "Common error"

    }
}