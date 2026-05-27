package com.example.adopcionmascotas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.adopcionmascotas.presentation.screens.LoginScreen
import com.example.adopcionmascotas.presentation.screens.PetListScreen
import com.example.adopcionmascotas.presentation.viewmodel.PetViewModel
import com.example.adopcionmascotas.presentation.theme.AdopcionMascotasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdopcionMascotasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val petViewModel: PetViewModel = viewModel()

                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(onLoginSuccess = {
                                navController.navigate("pet_list") {
                                    popUpTo("login") { inclusive = true }
                                }
                            })
                        }
                        composable("pet_list") {
                            PetListScreen(
                                viewModel = petViewModel,
                                onPetClick = { /* Ir a detalle */ },
                                onAddPetClick = { /* Ir a agregar */ }
                            )
                        }
                    }
                }
            }
        }
    }
}
