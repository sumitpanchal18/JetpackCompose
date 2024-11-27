package com.example.composekotlin.contact.retrofit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.composekotlin.contact.room.ApiViewModelFactory
import com.example.composekotlin.contact.room.AppDatabase
import com.example.composekotlin.contact.room.UserEntity

@Composable
fun UserListScreen(navController: NavHostController) {
    val context = LocalContext.current
    val apiService = RetrofitInstance.apiService
    val userDao = AppDatabase.getDatabase(context).userDao()

    val apiViewModel: ApiViewModel = viewModel(
        factory = ApiViewModelFactory(apiService, userDao)
    )

    val users = apiViewModel.users.value
    val loading = apiViewModel.loading.value

    LaunchedEffect(Unit) {
        apiViewModel.fetchUsersFromApi()
    }

    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("addContact")
                },
                containerColor = Color(0xFF6343B0)
            ) {
                Text("+", color = Color.White, fontSize = 32.sp)
            }

        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)
                    .background(Color.White)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.Center

            ) {
                Text(
                    "Contact List", style = TextStyle(
//                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                )
            }

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
                    Text("No users available.")
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(users) { user ->
                            UserItem(user = user, onClick = { selectedUser ->
                                navController.navigate("updateContact/${selectedUser.id}")
                            })
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun UserItem(user: UserEntity, onClick: (UserEntity) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp, 10.dp)
            .background(
                Color(0xFF6793B0),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(18.dp)
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onClick(user) }
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                user.name,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            )
            Text("Phone : ${user.phone}", style = TextStyle(color = Color.White))
            Text("Email : ${user.email}", style = TextStyle(color = Color.White))
            Text("Website : ${user.website}", style = TextStyle(color = Color.White))
        }
    }
}
