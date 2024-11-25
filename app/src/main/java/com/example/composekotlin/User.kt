package com.example.composekotlin
import retrofit2.http.GET

data class ApiResponse(val id: Int, val name: String)

interface ApiService {
    @GET("users")
    suspend fun getData(): List<ApiResponse>
}
