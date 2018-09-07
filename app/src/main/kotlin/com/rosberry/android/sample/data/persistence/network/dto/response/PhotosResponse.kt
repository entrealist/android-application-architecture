package com.rosberry.android.sample.data.persistence.network.dto.response

import com.google.gson.annotations.SerializedName

class PhotosResponse : ArrayList<PhotosResponse.PhotoData>() {
    
    class PhotoData(@SerializedName("albumId") val albumId: Int,
                    @SerializedName("id") val id: String,
                    @SerializedName("title") val title: String,
                    @SerializedName("url") val url: String,
                    @SerializedName("thumbnailUrl") val thumbnailUrl: String)

}