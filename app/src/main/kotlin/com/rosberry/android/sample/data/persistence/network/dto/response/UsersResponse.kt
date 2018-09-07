package com.rosberry.android.sample.data.persistence.network.dto.response

import com.google.gson.annotations.SerializedName

class UsersResponse : ArrayList<UsersResponse.UserData>() {

    class UserData(@SerializedName("name") val name: String,
                   @SerializedName("id") val id: Int,
                   @SerializedName("username") val username: String,
                   @SerializedName("email") val email: String,
                   @SerializedName("phone") val website: String,
                   @SerializedName("company") val company: CompanyData,
                   @SerializedName("address") val address: AddressData
    )

    class CompanyData(@SerializedName("name") val name: String,
                      @SerializedName("catchPhrase") val catchPhrase: String,
                      @SerializedName("bs") val slogan: String)

    class AddressData(@SerializedName("street") val street: String,
                      @SerializedName("suite") val suite: String,
                      @SerializedName("city") val city: String,
                      @SerializedName("zipcode") val zipcode: String,
                      @SerializedName("geo") val location: Geo)

    class Geo(@SerializedName("lat") val lat: String,
              @SerializedName("lng") val lng: String)
}