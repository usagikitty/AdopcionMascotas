package com.example.adopcionmascotas.ui.pets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adopcionmascotas.data.repository.PetRepositoryImpl
import com.example.adopcionmascotas.domain.model.Pet
import com.example.adopcionmascotas.domain.repository.PetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PetViewModel(
    private val repository: PetRepository = PetRepositoryImpl()
) : ViewModel() {

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
                _pets.value = repository.getAllPets()
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}
