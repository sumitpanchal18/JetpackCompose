package com.example.composekotlin.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composekotlin.contact.retrofit.ApiViewModel
import com.example.composekotlin.contact.retrofit.RetrofitInstance
import com.example.composekotlin.contact.retrofit.UserListScreen
import com.example.composekotlin.contact.room.AppDatabase
import com.example.composekotlin.contact.room.UserDao

class MainActivity : ComponentActivity() {
    // Lazy initialization to avoid premature context usage
    private val userDao: UserDao by lazy {
        AppDatabase.getDatabase(this).userDao()
    }

    private val apiService by lazy {
        RetrofitInstance.apiService
    }

    private val apiViewModel by lazy {
        ApiViewModel(apiService, userDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(apiViewModel)
        }
    }
}

@Composable
fun MyApp(viewModel: ApiViewModel) {
    NavController(viewModel)
}

@Composable
fun NavController(viewModel: ApiViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "userList"
    ) {
        composable("userList") {
            UserListScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("details") {
            DetailsScreen(navController)
        }
        composable("other") {
            PracticeScreen()
        }
        composable("addContact") {
            AddContact(navController, viewModel = viewModel)
        }
        composable("updateContact") {
            UpdateContact(navController)
        }
    }
}

@Composable
fun PracticeScreen() {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { showDialog = true }) {
            Text("Submit")
        }

        SimpleDialog(showDialog = showDialog, onDismiss = {
            Log.d("Dialog", "Dismissed")
            showDialog = false
        })
    }
}

@Composable
fun SimpleDialog(showDialog: Boolean, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Title") },
            text = { Text("This is a simple dialog.") },
            confirmButton = {
                Button(onClick = { onDismiss() }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home Screen",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("details") }) {
            Text("Go to Details Screen")
        }
    }
}

@Composable
fun DetailsScreen(navController: NavHostController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Details Screen",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        ToggleExample(context)

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("other") }) {
            Text("Go to Practice Screen")
        }
    }
}

@Composable
fun ToggleExample(context: Context) {
    var isChecked by remember { mutableStateOf(false) }
    var isSwitched by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Toggle Example", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                Toast.makeText(context, "Button clicked!", Toast.LENGTH_SHORT).show()
                Log.d("ButtonClick", "Button was clicked!")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Click Me", color = Color.White, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
            Text(
                text = if (isChecked) "Checkbox is Checked" else "Checkbox is Unchecked",
                color = if (isChecked) Color.Green else Color.Red
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = isSwitched,
                onCheckedChange = { isSwitched = it }
            )
            Text("Switch", modifier = Modifier.padding(start = 8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyApp() {
    MyApp(viewModel = ApiViewModel(RetrofitInstance.apiService, AppDatabase.getDatabase(LocalContext.current).userDao()))
}
