package com.example.adopcionmascotas.data.mapper

import com.example.adopcionmascotas.data.remote.dto.PetDto
import com.example.adopcionmascotas.domain.model.Pet

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
