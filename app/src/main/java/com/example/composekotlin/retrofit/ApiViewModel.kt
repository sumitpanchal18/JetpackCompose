package com.example.composekotlin.retrofit
// ApiViewModel.kt
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ApiViewModel : ViewModel() {
    private val _users = mutableStateOf<List<User>>(emptyList())
    val users: State<List<User>> get() = _users

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> get() = _loading

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getUsers()
                _users.value = response
            } catch (e: Exception) {
                _users.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }
}
