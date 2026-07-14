package com.example.adopcionmascotas.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.adopcionmascotas.presentation.theme.*
import com.example.adopcionmascotas.presentation.viewmodel.AuthViewModel
import com.example.adopcionmascotas.presentation.viewmodel.PetViewModel

@Composable
fun MainScreen(
    petViewModel: PetViewModel,
    authViewModel: AuthViewModel,
    onLogout: () -> Unit,
    onAddPetClick: () -> Unit
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = UsagiPink
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Inicio") },
                    selected = selectedItem == 0,
                    onClick = { selectedItem = 0 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = UsagiPink,
                        selectedTextColor = UsagiPink,
                        indicatorColor = UsagiLightPink
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Perfil") },
                    selected = selectedItem == 1,
                    onClick = { selectedItem = 1 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = UsagiPink,
                        selectedTextColor = UsagiPink,
                        indicatorColor = UsagiLightPink
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedItem) {
                0 -> PetListScreen(
                    viewModel = petViewModel,
                    onPetClick = { /* Ver detalle o editar */ },
                    onAddPetClick = onAddPetClick
                )
                1 -> ProfileScreen(
                    viewModel = authViewModel,
                    onLogout = onLogout
                )
            }
        }
    }
}
