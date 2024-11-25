package com.example.composekotlin.retrofit
// ApiService.kt
import retrofit2.http.GET

interface ApiService {
    @GET("users") // Replace with your API endpoint
    suspend fun getUsers(): List<User>
}
