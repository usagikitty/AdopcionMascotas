package com.example.adopcionmascotas.data.mapper

import com.example.adopcionmascotas.data.remote.dto.PetDto
import com.example.adopcionmascotas.domain.model.Pet

fun PetDto.toPet(): Pet {
    return Pet(
        id = id ?: 0L,
        name = nombre ?: "Sin nombre",
        breed = especie ?: "Desconocida",
        age = edad ?: 0,
        description = descripcion ?: "Sin descripción",
        isAvailable = disponible ?: true
    )
}

fun Pet.toDto(): PetDto {
    return PetDto(
        id = if (id == 0L) null else id,
        nombre = name,
        especie = breed,
        edad = age,
        descripcion = description,
        disponible = isAvailable
    )
}
