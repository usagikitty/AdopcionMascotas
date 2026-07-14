package com.example.adopcionmascotas.data.repository

import com.example.adopcionmascotas.data.local.TokenManager
import com.example.adopcionmascotas.data.remote.api.RetrofitClient
import com.example.adopcionmascotas.data.remote.dto.SolicitudAdopcionRequest
import com.example.adopcionmascotas.data.mapper.toPet
import com.example.adopcionmascotas.data.mapper.toDto
import com.example.adopcionmascotas.domain.model.Pet
import com.example.adopcionmascotas.domain.repository.PetRepository

class PetRepositoryImpl(
    tokenManager: TokenManager
) : PetRepository {
    
    private val api = RetrofitClient.getService(tokenManager)

    override suspend fun getAllPets(): List<Pet> {
        return try {
            api.getPets().map { it.toPet() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun addPet(pet: Pet): Result<Pet> {
        return try {
            val response = api.addPet(pet.toDto())
            Result.success(response.toPet())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updatePet(pet: Pet): Result<Pet> {
        return try {
            val response = api.updatePet(pet.id, pet.toDto())
            Result.success(response.toPet())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deletePet(id: Long): Result<Unit> {
        return try {
            api.deletePet(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun adoptPet(id: Long): Result<Unit> {
        return try {
            api.createSolicitud(SolicitudAdopcionRequest(mascotaId = id))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
