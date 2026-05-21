package com.fatec.at2_base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.fatec.at2_base.ui.screen.MovieScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MovieApp()
        }
    }
}

@Composable
fun MovieApp() {
    MovieScreen()
}

@Preview(showBackground = true)
@Composable
fun MovieAppPreview() {
    MovieApp()
}