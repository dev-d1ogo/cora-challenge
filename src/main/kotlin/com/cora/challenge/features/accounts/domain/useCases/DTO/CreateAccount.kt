package com.cora.challenge.features.accounts.domain.useCases.DTO

data class CreateAccountInput (val name: String, val cpf: String)
data class CreateAccountOutput (val message: String, val success: Boolean = true)