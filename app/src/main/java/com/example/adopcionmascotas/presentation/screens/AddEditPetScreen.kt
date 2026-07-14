package com.example.adopcionmascotas.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.adopcionmascotas.domain.model.Pet
import com.example.adopcionmascotas.presentation.viewmodel.PetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPetScreen(
    viewModel: PetViewModel,
    pet: Pet? = null,
    onNavigateBack: () -> Unit
) {
    var nombre by remember { mutableStateOf(pet?.name ?: "") }
    var especie by remember { mutableStateOf(pet?.breed ?: "") }
    var edad by remember { mutableStateOf(pet?.age?.toString() ?: "") }
    var descripcion by remember { mutableStateOf(pet?.description ?: "") }
    var disponible by remember { mutableStateOf(pet?.isAvailable ?: true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (pet == null) "Nueva Mascota" else "Editar Mascota") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = especie,
                onValueChange = { especie = it },
                label = { Text("Especie (Perro, Gato, etc.)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = edad,
                onValueChange = { if (it.all { char -> char.isDigit() }) edad = it },
                label = { Text("Edad (años)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Checkbox(checked = disponible, onCheckedChange = { disponible = it })
                Text("Disponible para adopción")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val newPet = Pet(
                        id = pet?.id ?: 0L,
                        name = nombre,
                        breed = especie,
                        age = edad.toIntOrNull() ?: 0,
                        description = descripcion,
                        isAvailable = disponible
                    )
                    if (pet == null) viewModel.addPet(newPet) else viewModel.updatePet(newPet)
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nombre.isNotBlank() && especie.isNotBlank()
            ) {
                Text("Guardar")
            }
        }
    }
}
