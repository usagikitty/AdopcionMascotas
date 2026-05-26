package com.example.adopcionmascotas.data.repository

import android.util.Log
import com.example.adopcionmascotas.data.remote.PetApiService
import com.example.adopcionmascotas.data.remote.dto.toPet
import com.example.adopcionmascotas.domain.model.Pet
import com.example.adopcionmascotas.domain.repository.PetRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PetRepositoryImpl : PetRepository {
    
    private val api = Retrofit.Builder()
        .baseUrl("https://huachitos.cl/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PetApiService::class.java)

    override suspend fun getAllPets(): List<Pet> {
        return try {
            val response = api.getPets()
            Log.d("PetRepository", "Fetched ${response.data.size} pets")
            response.data.map { it.toPet() }
        } catch (e: Exception) {
            Log.e("PetRepository", "Error fetching pets", e)
            emptyList()
        }
    }
}
