package com.rosberry.android.sample.data.persistence.network.dto.response

import com.google.gson.annotations.SerializedName

class PostResponse(@SerializedName("userId") val userId: Int,
                   @SerializedName("id") val id: String,
                   @SerializedName("title") val title: String,
                   @SerializedName("body") val description: String) : BaseResponse() {

}