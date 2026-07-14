package com.example.adopcionmascotas.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adopcionmascotas.presentation.theme.*
import com.example.adopcionmascotas.presentation.viewmodel.AuthViewModel

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun ProfileScreen(
    viewModel: AuthViewModel,
    onLogout: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchPerfil()
        viewModel.fetchMisSolicitudes()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(UsagiBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mi Perfil",
            style = MaterialTheme.typography.headlineMedium,
            color = UsagiAccent,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Surface(
            modifier = Modifier.size(100.dp),
            color = UsagiLightPink,
            shape = RoundedCornerShape(50.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(50.dp), tint = UsagiPink)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                ProfileItem(
                    label = "Nombre", 
                    value = viewModel.userProfile?.nombre ?: "Cargando...", 
                    icon = Icons.Default.Person
                )
                Divider(modifier = Modifier.padding(vertical = 8.dp), color = UsagiLightPink)
                ProfileItem(
                    label = "Correo", 
                    value = viewModel.userProfile?.email ?: "Cargando...", 
                    icon = Icons.Default.Person 
                )
                Divider(modifier = Modifier.padding(vertical = 8.dp), color = UsagiLightPink)
                ProfileItem(
                    label = "Teléfono", 
                    value = viewModel.userProfile?.telefono ?: (if(viewModel.userProfile == null) "Cargando..." else "No registrado"),
                    icon = Icons.Default.Phone
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Mis Solicitudes",
            style = MaterialTheme.typography.titleLarge,
            color = UsagiAccent,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.userSolicitudes) { solicitud ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = solicitud.mascotaNombre ?: "Mascota", fontWeight = FontWeight.Bold)
                            Text(text = solicitud.fecha ?: "", fontSize = 12.sp, color = Color.Gray)
                        }
                        Badge(
                            containerColor = if (solicitud.estado == "APROBADA") Color(0xFFC8E6C9) else UsagiLightPink
                        ) {
                            Text(text = solicitud.estado ?: "PENDIENTE", color = UsagiAccent)
                        }
                    }
         }
            }
            if (viewModel.userSolicitudes.isEmpty()) {
                item {
                    Text("No tienes solicitudes aún", color = Color.Gray, modifier = Modifier.padding(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { 
                viewModel.logout()
                onLogout()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = null, tint = UsagiAccent)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Cerrar Sesión", color = UsagiAccent, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ProfileItem(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = UsagiPink, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = label, fontSize = 12.sp, color = Color.Gray)
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = UsagiText)
        }
    }
}
