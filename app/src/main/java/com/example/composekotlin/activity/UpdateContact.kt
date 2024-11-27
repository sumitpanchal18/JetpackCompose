package com.example.composekotlin.activity

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.composekotlin.contact.retrofit.ApiViewModel
import com.example.composekotlin.contact.room.UserEntity
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun UpdateContact(
    navController: NavHostController,
    viewModel: ApiViewModel,
    userId: Int
) {
    val coroutineScope = rememberCoroutineScope()

    // State variables for the form fields
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }
    var user by remember { mutableStateOf<UserEntity?>(null) }

    // Load user data when the composable is first created
    LaunchedEffect(userId) {
        coroutineScope.launch {
            Log.d("UpdateContact", "Fetching user with ID: $userId")
            val fetchedUser = viewModel.getUserById(userId)
            if (fetchedUser != null) {
                user = fetchedUser
                Log.d("UpdateContact", "Fetched user: $fetchedUser")
            } else {
                Log.d("UpdateContact", "No user found with ID: $userId")
            }
        }
    }

    LaunchedEffect(user) {
        user?.let {
            name = it.name
            phoneNumber = it.phone
            email = it.email
            website = it.website
            Log.d("UpdateContact", "User data set: $name, $phoneNumber, $email, $website")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Update Contact",
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 32.sp,
            )
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
        )

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(text = user?.name ?: "No user found")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = website,
            onValueChange = { website = it },
            label = { Text("Website") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Uri
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Button(
            onClick = {
                if (name.isNotBlank() && phoneNumber.isNotBlank()) {
                    val updatedContact = user?.copy(
                        name = name,
                        phone = phoneNumber,
                        email = email,
                        website = website
                    )
                    if (updatedContact != null) {
                        viewModel.updateContact(updatedContact)
                    }
                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .padding(top = 28.dp)
        ) {
            Text("Update Contact".toUpperCase(Locale.ROOT))
        }

        Button(
            onClick = {
                user?.let {
                    viewModel.deleteContact(it)
                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Delete Contact", color = Color.White)
        }
    }
}
