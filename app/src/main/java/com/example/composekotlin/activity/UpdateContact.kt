package com.example.composekotlin.activity


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun UpdateContact(navController: NavHostController? = null) {

    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }

    val maxLengthPhone = 10

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
                fontSize = 32.sp
            )
        )

        OutlinedTextField(
            value = name,
            singleLine = true,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
        )

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                if (it.length <= maxLengthPhone) {
                    phoneNumber = it
                }
            },
            label = { Text("Phone Number") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone

            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
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

        // Update button
        Button(
            onClick = {
                navController?.popBackStack()
            },
            modifier = Modifier
                .padding(top = 28.dp)
        ) {
            Text("Update Contact".toUpperCase())
        }

        Button(
            onClick = {
                navController?.navigate("home") {
                    popUpTo("userList") { inclusive = true }  // Clears the back stack up to 'home'
                }
            },
            modifier = Modifier
                .padding(top = 12.dp)
        ) {
            Text("Delete Contact".toUpperCase())
        }
    }
}
