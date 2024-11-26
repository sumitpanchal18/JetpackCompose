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
    private val apiService: ApiService, // Retrofit service for fetching data from the API
    private val userDao: UserDao       // Room DAO for interacting with the database
) : ViewModel() {

    // State to hold the list of users and loading state
    val users = mutableStateOf<List<UserEntity>>(emptyList())
    val loading = mutableStateOf(false)

    // Function to fetch users from the API and store them in Room database
    fun fetchUsersFromApi() {
        viewModelScope.launch {
            loading.value = true
            try {
                val response: Response<List<UserEntity>> = apiService.getUsers()

                // Log the response from the API
                if (response.isSuccessful && response.body() != null) {
                    val fetchedUsers = response.body()!!
                    // Log the fetched users
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
            // Insert the users into the database
            userDao.insertAll(usersList)

            // Fetch the users from the database
            val usersFromDb = userDao.getAllUsers()
            Log.d("ApiViewModel", "Users from DB after insertion: $usersFromDb")

            // Update the UI state with the data from the database
            withContext(Dispatchers.Main) {
                users.value = usersFromDb
            }
        }
    }


}
