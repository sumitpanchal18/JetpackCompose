package com.example.composekotlin.contact.retrofit

import com.example.composekotlin.contact.room.ApiResponse
import com.example.composekotlin.contact.room.UserEntity
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("users")  // Replace with your API endpoint
    suspend fun getUsers(): Response<List<UserEntity>>
}
