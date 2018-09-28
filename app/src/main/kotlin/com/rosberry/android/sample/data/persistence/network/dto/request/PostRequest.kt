package com.rosberry.android.sample.data.persistence.network.dto.request

class PostRequest(
        val userId: Int,
        val title: String,
        val description: String
) : BaseRequest() {

}