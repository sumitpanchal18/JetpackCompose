package com.example.composekotlin

import retrofit2.http.GET

interface ApiService {
    @GET("users") // Replace with your actual endpoint
    suspend fun getUsers(): List<User>
}
