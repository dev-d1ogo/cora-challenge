package com.cora.challenge.features.accounts.presentation.DTO

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class AccountRequestDTO(
    @field:NotBlank(message = "name is required")
    val name: String? = null,

    @field:NotBlank(message = "cpf is required")
    @field:Pattern(
        regexp = REGEX_CPF,
        message = "CPF must contain 11 digits"
    )
    val cpf: String? = null
) {
    companion object {
        const val REGEX_CPF = "^[0-9]{11}$"
    }
}
