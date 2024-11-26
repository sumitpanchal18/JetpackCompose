package com.example.composekotlin.contact.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composekotlin.contact.retrofit.ApiService
import com.example.composekotlin.contact.retrofit.ApiViewModel

class ApiViewModelFactory(
    private val apiService: ApiService,
    private val userDao: UserDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ApiViewModel(apiService, userDao) as T
    }
}
