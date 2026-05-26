package com.example.adopcionmascotas.data.remote

import com.example.adopcionmascotas.data.remote.dto.HuachitosResponse
import retrofit2.http.GET

interface PetApiService {
    @GET("animales")
    suspend fun getPets(): HuachitosResponse
}
