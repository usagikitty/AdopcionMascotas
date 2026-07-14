package com.example.adopcionmascotas.domain.repository

import com.example.adopcionmascotas.data.remote.dto.LoginResponse
import com.example.adopcionmascotas.data.remote.dto.RegisterRequest
import com.example.adopcionmascotas.data.remote.dto.SolicitudDto

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<LoginResponse>
    suspend fun register(nombre: String, email: String, password: String, telefono: String): Result<Unit>
    suspend fun getPerfil(): Result<RegisterRequest>
    suspend fun getMisSolicitudes(): Result<List<SolicitudDto>>
    suspend fun saveToken(token: String)
    suspend fun logout()
}
