package com.example.composekotlin.contact.retrofit

import com.example.composekotlin.contact.room.UserEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<UserEntity>>

    @POST("users")
    suspend fun addUser(@Body user: UserEntity)


}