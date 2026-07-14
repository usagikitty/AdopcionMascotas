package com.example.adopcionmascotas.data.remote.api

import com.example.adopcionmascotas.data.remote.dto.*
import retrofit2.http.*

interface PetApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("usuarios")
    suspend fun register(@Body request: RegisterRequest): Unit

    @GET("mascotas")
    suspend fun getPets(): List<PetDto>

    @POST("mascotas")
    suspend fun addPet(@Body pet: PetDto): PetDto

    @PUT("mascotas/{id}")
    suspend fun updatePet(@Path("id") id: Long, @Body pet: PetDto): PetDto

    @DELETE("mascotas/{id}")
    suspend fun deletePet(@Path("id") id: Long): Unit

    // --- USUARIOS Y PERFIL ---
    @GET("usuarios/me")
    suspend fun getPerfil(): RegisterRequest

    // --- SOLICITUDES DE ADOPCIÓN ---
    @POST("solicitudes")
    suspend fun createSolicitud(@Body request: SolicitudAdopcionRequest): Unit

    @GET("solicitudes")
    suspend fun getTodasLasSolicitudes(): List<SolicitudDto>
}
