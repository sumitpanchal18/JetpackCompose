package com.example.composekotlin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            MainScreen()
        }
    }
}
@Composable
fun MainScreen() {
    val context = LocalContext.current
    var buttonText by remember { mutableStateOf("Click Me") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Welcome to Jetpack Compose!",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                buttonText = "Clicked!"
                Toast.makeText(context, "Button Clicked!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .height(45.dp)
                .width(150.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = buttonText,
                fontSize = 21.sp,
                color = Color.White
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMyApp() {
    MyApp()
}
