package com.example.adopcionmascotas.presentation.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.adopcionmascotas.data.local.TokenManager
import com.example.adopcionmascotas.data.repository.PetRepositoryImpl
import com.example.adopcionmascotas.domain.model.Pet
import com.example.adopcionmascotas.domain.repository.PetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PetViewModel(
    application: Application,
    private val repository: PetRepository = PetRepositoryImpl(TokenManager(application))
) : AndroidViewModel(application) {

    var toastMessage by mutableStateOf<String?>(null)
        private set

    fun clearToast() { toastMessage = null }

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets: StateFlow<List<Pet>> = _pets

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadPets()
    }

    fun loadPets() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getAllPets()
                _pets.value = result
            } catch (e: Exception) {
                _pets.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addPet(pet: Pet) {
        viewModelScope.launch {
            repository.addPet(pet).onSuccess { loadPets() }
        }
    }

    fun updatePet(pet: Pet) {
        viewModelScope.launch {
            repository.updatePet(pet).onSuccess { loadPets() }
        }
    }

    fun deletePet(id: Long) {
        viewModelScope.launch {
            repository.deletePet(id).onSuccess { loadPets() }
        }
    }

    fun adoptPet(petId: Long) {
        viewModelScope.launch {
            repository.adoptPet(petId).onSuccess {
                toastMessage = "¡Solicitud de adopción enviada!"
                loadPets()
            }.onFailure { error ->
                toastMessage = "Error: ${error.localizedMessage ?: "Verifica tu conexión"}"
            }
        }
    }
}
