package com.rosberry.android.sample.data.persistence.network.dto.response

import com.google.gson.annotations.SerializedName

class PostResponse(@SerializedName("id") val id: Int,
                   @SerializedName("userId") val userId: Int,
                   @SerializedName("title") val title: String?,
                   @SerializedName("body") val description: String?) : BaseResponse() {

}