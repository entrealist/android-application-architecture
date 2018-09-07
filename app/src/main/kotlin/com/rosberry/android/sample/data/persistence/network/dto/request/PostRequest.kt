package com.rosberry.android.sample.data.persistence.network.dto.request

import com.rosberry.android.sample.data.persistence.network.dto.BaseRequest

class PostRequest(val userId: Int,
                  val title: String,
                  val description: String) : BaseRequest() {
}