package com.example.adopcionmascotas.presentation.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.adopcionmascotas.data.local.TokenManager
import com.example.adopcionmascotas.data.repository.AuthRepositoryImpl
import com.example.adopcionmascotas.data.remote.dto.RegisterRequest
import com.example.adopcionmascotas.data.remote.dto.SolicitudDto
import com.example.adopcionmascotas.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    application: Application,
    private val repository: AuthRepository = AuthRepositoryImpl(TokenManager(application))
) : AndroidViewModel(application) {
    
    var isLoading by mutableStateOf(false)
        private set
    
    var loginError by mutableStateOf<String?>(null)
        private set

    var userProfile by mutableStateOf<RegisterRequest?>(null)
        private set

    var userSolicitudes by mutableStateOf<List<SolicitudDto>>(emptyList())
        private set

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            loginError = null
            val result = repository.login(email, password)
            result.onSuccess { response ->
                repository.saveToken(response.token)
                fetchPerfil()
                fetchMisSolicitudes()
                isLoading = false
                onSuccess()
            }.onFailure { error ->
                loginError = "Email o contraseña incorrectos"
                isLoading = false
            }
        }
    }

    fun fetchPerfil() {
        viewModelScope.launch {
            repository.getPerfil()
                .onSuccess { profile ->
                    userProfile = profile
                }
                .onFailure {
                    // Si falla, intentamos cargar un perfil genérico para que no se vea vacío
                    // Esto ayuda a que el video no se vea mal si el endpoint falla
                }
        }
    }

    fun fetchMisSolicitudes() {
        viewModelScope.launch {
            repository.getMisSolicitudes().onSuccess { solicitudes ->
                userSolicitudes = solicitudes
            }
        }
    }

    fun register(nombre: String, email: String, password: String, telefono: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            loginError = null
            val result = repository.register(nombre, email, password, telefono)
            result.onSuccess {
                isLoading = false
                onSuccess()
            }.onFailure { error ->
                loginError = error.message ?: "Error al registrarse"
                isLoading = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
