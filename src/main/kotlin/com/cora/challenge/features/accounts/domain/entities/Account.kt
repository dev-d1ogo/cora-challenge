package com.cora.challenge.features.accounts.domain.entities

import java.util.*
import java.util.UUID.randomUUID

data class Account(
    val id: String = randomUUID().toString(),
    val name: String,
    val cpf: String,
    val createdAt: Date
) {
    companion object{
        fun create(
            name: String,
            cpf: String
        ) = Account(
            name = name,
            cpf = cpf,
            createdAt = Date()
        )
    }
}
