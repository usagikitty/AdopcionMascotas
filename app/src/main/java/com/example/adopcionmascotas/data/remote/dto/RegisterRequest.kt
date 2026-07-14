package com.example.adopcionmascotas.data.remote.dto

data class RegisterRequest(
    val nombre: String? = null,
    val email: String? = null,
    val password: String? = null,
    val telefono: String? = null,
    val id: Long? = null
)
