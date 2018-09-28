package com.rosberry.android.sample.entity

import com.rosberry.android.sample.data.persistence.network.dto.response.data.GeoData

class Address(val street: String,
              val suite: String,
              val city: String,
              val zipcode: String) {

    val location: GeoData? = null
}