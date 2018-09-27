package com.rosberry.android.sample.entity

class User(val id: Int,
           val name: String,
           val userName: String) {
    var email: String? = ""
    var website: String = ""
    var company: Company? = null
    var address: Address? = null
}