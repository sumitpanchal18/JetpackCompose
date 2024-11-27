package com.example.composekotlin.contact.retrofit

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composekotlin.contact.room.UserDao
import com.example.composekotlin.contact.room.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ApiViewModel(
    private val apiService: ApiService,
    private val userDao: UserDao
) : ViewModel() {

    val users = mutableStateOf<List<UserEntity>>(emptyList())
    val loading = mutableStateOf(false)
    fun addContact(userEntity: UserEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userDao.insert(userEntity)

                val updatedUsers = userDao.getAllUsers()
                withContext(Dispatchers.Main) {
                    users.value = updatedUsers
                }
            }
        }
    }

    fun fetchUsersFromApi() {
        viewModelScope.launch {
            loading.value = true
            try {
                val response: Response<List<UserEntity>> = apiService.getUsers()

                if (response.isSuccessful && response.body() != null) {
                    val fetchedUsers = response.body()!!
                    println("Fetched users from API: $fetchedUsers")
                    insertUsersIntoDatabase(fetchedUsers)
                } else {
                    println("Failed to fetch users from API")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                loading.value = false
            }
        }
    }

    private suspend fun insertUsersIntoDatabase(usersList: List<UserEntity>) {
        withContext(Dispatchers.IO) {
            userDao.insertAll(usersList)

            val usersFromDb = userDao.getAllUsers()
            Log.d("ApiViewModel", "Users from DB after insertion: $usersFromDb")

            withContext(Dispatchers.Main) {
                users.value = usersFromDb
            }
        }
    }


}
