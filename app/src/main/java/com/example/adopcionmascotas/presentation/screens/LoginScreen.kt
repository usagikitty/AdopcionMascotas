package com.example.adopcionmascotas.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.adopcionmascotas.presentation.viewmodel.AuthViewModel
import com.example.adopcionmascotas.presentation.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(UsagiBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo o Icono de Conejito (Placeholder)
        Surface(
            modifier = Modifier.size(100.dp),
            color = UsagiLightPink,
            shape = RoundedCornerShape(50.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("🐰", fontSize = 50.sp)
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Adopta con Amor", 
            style = MaterialTheme.typography.headlineLarge,
            color = UsagiAccent,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Encuentra a tu compañero ideal", 
            color = UsagiText,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(48.dp))
        
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = UsagiPink,
                unfocusedBorderColor = Color.LightGray,
                focusedLabelColor = UsagiPink
            ),
            enabled = !viewModel.isLoading
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = UsagiPink,
                unfocusedBorderColor = Color.LightGray,
                focusedLabelColor = UsagiPink
            ),
            enabled = !viewModel.isLoading
        )
        
        if (viewModel.loginError != null) {
            Text(
                text = viewModel.loginError!!,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { viewModel.login(email, password, onLoginSuccess) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = UsagiPink),
            shape = RoundedCornerShape(28.dp),
            enabled = !viewModel.isLoading && email.isNotBlank() && password.isNotBlank()
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Iniciar Sesión", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(onClick = onRegisterClick) {
            Text("¿No tienes cuenta? ", color = UsagiText)
            Text("Regístrate", color = UsagiAccent, fontWeight = FontWeight.Bold)
        }
    }
}
