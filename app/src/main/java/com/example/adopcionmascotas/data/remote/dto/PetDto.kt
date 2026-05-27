package com.example.adopcionmascotas.data.remote.dto

data class HuachitosResponse(
    val data: List<PetDto>
)

data class PetDto(
    val id: Int,
    val nombre: String,
    val tipo: String,
    val edad: String,
    val imagen: String,
    val desc_fisica: String,
    val desc_personalidad: String
)
