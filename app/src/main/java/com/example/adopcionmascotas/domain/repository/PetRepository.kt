package com.example.adopcionmascotas.domain.repository

import com.example.adopcionmascotas.domain.model.Pet

interface PetRepository {
    suspend fun getAllPets(): List<Pet>
}
