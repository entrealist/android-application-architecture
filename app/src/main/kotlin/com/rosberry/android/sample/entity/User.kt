package com.rosberry.android.sample.entity

import com.rosberry.android.sample.data.persistence.network.dto.response.UsersResponse

class User(val id: Int,
           val name: String,
           val userName: String) {
    var email: String? = ""
    var website: String = ""
    var company: Company? = null
    var address: Address? = null
}