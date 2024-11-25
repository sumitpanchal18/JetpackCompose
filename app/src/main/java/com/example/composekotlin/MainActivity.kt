package com.example.composekotlin

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.composekotlin.retrofit.UserListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
//    NavController()
    UserListScreen()
}

@Composable
fun NavController() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController)
        }
        composable("details") {
            DetailsScreen(navController)
        }
        composable("other") {
            PracticeScreen(navController)
        }
    }
}

@Composable
fun PracticeScreen(navController: NavHostController) {
//    SimpleList()
    var showDialog by remember { mutableStateOf(false) }

    // Button to show the dialog
    Button(onClick = { showDialog = true }) {
        Text("Submit")
    }

    // Call the SimpleDialog and pass the state and dismiss callback
    SimpleDialog(showDialog = showDialog, onDismiss = {
        println("Dismissed")
        showDialog = false // Dismiss the dialog by setting the state to false
    })


//    InputField()
}

@Composable
fun InputField() {
    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter Text") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("You typed: $text")
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
fun SimpleList() {
    LazyColumn {
        items(100) { index ->
            Text("Item No : ${index + 1}", modifier = Modifier.padding(8.dp))
        }
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
        Button(onClick = {
            navController.navigate("details")
        }) {
            Text("Go to Details Screen")
        }
    }
}

@Composable
fun DetailsScreen(navController: NavHostController) {

    val context = LocalContext.current
    ToggleExample(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Details Screen",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
//            navController.popBackStack()
            navController.navigate("other")
        }) {
            Text("Go Back to Home Screen")
        }
    }
}


@Composable
fun ToggleExample(context: Context) {
    var isChecked by remember {
        mutableStateOf(false)
    }
    var isSwitched by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sumit Panchal",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                Toast.makeText(context, "Button clicked!", Toast.LENGTH_SHORT).show()
                Log.d("ButtonClick", "Button was clicked!")
            }, modifier = Modifier
                .fillMaxWidth()
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

        Spacer(modifier = Modifier.height(10.dp))

//        BoxExample()

        Counter()

        /*
                 IconButton(
                     onClick = {
                         Toast.makeText(context, "Image Button Clicked!", Toast.LENGTH_SHORT).show()

                     },
                     modifier = Modifier.size(200.dp, 200.dp)
                 ) {
                     Image(
                         painter = painterResource(id = R.drawable.car),
                         contentDescription = "Button Image",
                         modifier = Modifier.fillMaxSize()
                     )
                 }*/
    }
}

@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Count: $count", fontSize = 24.sp)
        Button(onClick = { count++ }) {
            Text("Increment")
        }
    }
}

@Composable
fun BoxExample() {
    var sliderPosition by remember {
        mutableStateOf(0f)
    }

    Slider(
        value = sliderPosition,
        onValueChange = { sliderPosition = it },
        valueRange = 0f..100f,
        steps = 100,
        modifier = Modifier.padding(16.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewMyApp() {
    MyApp()
}
