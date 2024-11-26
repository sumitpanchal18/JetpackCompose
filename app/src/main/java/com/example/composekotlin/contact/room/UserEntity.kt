package com.example.composekotlin.contact.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val username: String,
    val email: String
)


data class ApiResponse(
    val id: Int,
    val name: String,
    val username: String,
    val email: String
)
