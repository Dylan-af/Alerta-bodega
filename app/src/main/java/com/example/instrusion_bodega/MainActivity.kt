package com.example.instrusion_bodega

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instrusion_bodega.ui.theme.Instrusion_bodegaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Instrusion_bodegaTheme {
                Navigation()
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "chooser") {
        composable("chooser") {
            ChooserScreen(navController = navController)
        }
        composable("client") {
            ClientScreen()
        }
        composable("dashboard") {
            DashboardScreen()
        }
    }
}