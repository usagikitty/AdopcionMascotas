package com.example.adopcionmascotas.data.remote.dto

import com.example.adopcionmascotas.domain.model.Pet

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

fun PetDto.toPet(): Pet {
    return Pet(
        id = id,
        name = nombre,
        breed = tipo,
        age = edad,
        imageUrl = imagen,
        description = "$desc_fisica $desc_personalidad".replace(Regex("<[^>]*>"), "")
    )
}
