package com.example.composekotlin.retrofit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UserListScreen() {
    val apiViewModel: ApiViewModel = viewModel()
    val users = apiViewModel.users.value
    val loading = apiViewModel.loading.value

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Color.White)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp)
                )
            } else if (users.isEmpty()) {
                Text("Failed to load data.")
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(users) { user ->
                        UserItem(user = user)
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
                .background(Color.White)
        )
    }
}

@Composable
fun UserItem(user: User) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp, 10.dp)
            .background(
                Color(0xFF6793B0),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(18.dp)
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Column {
            Text(
                text = "ID: ${user.id}",
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
            Text(text = "Name: ${user.name}")
            Text(text = "Username: ${user.username}")
            Text(text = "Email: ${user.email}")
        }
    }
}
