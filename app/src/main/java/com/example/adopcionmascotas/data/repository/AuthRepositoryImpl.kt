package com.example.adopcionmascotas.data.repository

import com.example.adopcionmascotas.data.local.TokenManager
import com.example.adopcionmascotas.data.remote.api.RetrofitClient
import com.example.adopcionmascotas.data.remote.dto.LoginRequest
import com.example.adopcionmascotas.data.remote.dto.LoginResponse
import com.example.adopcionmascotas.data.remote.dto.RegisterRequest
import com.example.adopcionmascotas.data.remote.dto.SolicitudDto
import com.example.adopcionmascotas.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val tokenManager: TokenManager
) : AuthRepository {

    private val api = RetrofitClient.getService(tokenManager)

    override suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = api.login(LoginRequest(email, password))
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(nombre: String, email: String, password: String, telefono: String): Result<Unit> {
        return try {
            api.register(RegisterRequest(nombre, email, password, telefono))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPerfil(): Result<RegisterRequest> {
        return try {
            val response = api.getPerfil()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMisSolicitudes(): Result<List<SolicitudDto>> {
        return try {
            val response = api.getTodasLasSolicitudes()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveToken(token: String) {
        tokenManager.saveToken(token)
    }

    override suspend fun logout() {
        tokenManager.deleteToken()
    }
}
