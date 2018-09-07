package com.rosberry.android.sample.data.persistence.network.dto.response

import com.google.gson.annotations.SerializedName

class PostsResponse : ArrayList<PostsResponse.PostData>() {

    class PostData(@SerializedName("userId") val userId: Int,
                   @SerializedName("id") val id: String,
                   @SerializedName("title") val title: String,
                   @SerializedName("body") val description: String)

}