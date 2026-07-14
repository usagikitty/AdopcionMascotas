package com.example.adopcionmascotas.domain.repository

import com.example.adopcionmascotas.domain.model.Pet

interface PetRepository {
    suspend fun getAllPets(): List<Pet>
    suspend fun addPet(pet: Pet): Result<Pet>
    suspend fun updatePet(pet: Pet): Result<Pet>
    suspend fun deletePet(id: Long): Result<Unit>
    suspend fun adoptPet(id: Long): Result<Unit>
}
