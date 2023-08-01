package com.surajrathod.mvidemo.network

import com.surajrathod.mvidemo.model.Users
import retrofit2.http.GET

interface NetworkInterface {

    @GET("users")
    suspend fun getUsers() : Users

}