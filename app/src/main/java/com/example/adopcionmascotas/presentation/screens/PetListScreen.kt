package com.example.adopcionmascotas.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adopcionmascotas.domain.model.Pet
import com.example.adopcionmascotas.presentation.viewmodel.PetViewModel

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetListScreen(
    viewModel: PetViewModel,
    onPetClick: (Pet) -> Unit,
    onAddPetClick: () -> Unit
) {
    val pets by viewModel.pets.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel.toastMessage) {
        viewModel.toastMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    // Gradiente original para el fondo
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFFFFBFE), Color(0xFFF3E5F5))
    )

    Scaffold(
        modifier = Modifier.background(backgroundGradient),
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "Adopta un Amigo", 
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp,
                        color = MaterialTheme.colorScheme.primary
                    ) 
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = onAddPetClick,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(
                    Icons.Default.Add, 
                    contentDescription = "Agregar", 
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (pets.isEmpty()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.Pets, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                    Text("No hay mascotas aún", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(pets) { pet ->
                        OriginalPetItem(
                            pet = pet, 
                            onClick = { onPetClick(pet) },
                            onDelete = { viewModel.deletePet(pet.id) },
                            onAdopt = { viewModel.adoptPet(pet.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OriginalPetItem(pet: Pet, onClick: () -> Unit, onDelete: () -> Unit, onAdopt: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Column {
            // ... (resto del contenido igual)
            
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = pet.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Row {
                        IconButton(onClick = onAdopt) {
                            Icon(Icons.Default.Favorite, contentDescription = "Adoptar", tint = Color(0xFFFF8C9E))
                        }
                        IconButton(onClick = onDelete) {
                            Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
                
                // ... (el resto del código sigue igual)
                
                Surface(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = pet.breed,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(16.dp))
                    Text(text = " ${pet.age} años", style = MaterialTheme.typography.bodyMedium)
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = pet.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    maxLines = 2
                )
                
                if (!pet.isAvailable) {
                    Badge(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("ADOPTADO", color = MaterialTheme.colorScheme.onErrorContainer)
                    }
                }
            }
        }
    }
}
