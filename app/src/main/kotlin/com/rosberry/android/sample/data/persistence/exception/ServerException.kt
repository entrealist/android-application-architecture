package com.rosberry.android.sample.data.persistence.exception

import retrofit2.HttpException
import retrofit2.Response

class ServerException(response: Response<*>) : HttpException(response) {
}