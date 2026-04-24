package com.cora.challenge.features.accounts.presentation

import com.cora.challenge.features.accounts.domain.useCases.CreateAccountUseCase
import com.cora.challenge.features.accounts.domain.useCases.DTO.CreateAccountInput
import com.cora.challenge.shared.exceptions.ConfllictException
import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/seed")
@Profile("dev-seed")
class SeedController(
    private val createAccountUseCase: CreateAccountUseCase,
) {
    private val seedAccounts = listOf(
        CreateAccountInput(
            name = "Ana Paula Ferreira",
            cpf = "11122233344"
        ),
        CreateAccountInput(
            name = "Bruno Carvalho",
            cpf = "22233344455"
        ),
        CreateAccountInput(
            name = "Carla Mendes",
            cpf = "33344455566"
        ),
        CreateAccountInput(
            name = "Diego Rocha",
            cpf = "44455566677"
        ),
        CreateAccountInput(
            name = "Elisa Teixeira",
            cpf = "55566677788"
        ),
    )

    @PostMapping
    fun seed(): ResponseEntity<Map<String, Any>> {
        val created = mutableListOf<String>()
        val skipped = mutableListOf<String>()

        seedAccounts.forEach { input ->
            try {
                createAccountUseCase.execute(input)
                created.add(input.name)
            } catch (e: ConfllictException) {
                skipped.add(input.name)
            }
        }

        return ResponseEntity.ok(
            mapOf(
                "created" to created,
                "skipped" to skipped,
                "message" to "Seed completed: ${created.size} created, ${skipped.size} ignored (already exists)"
            )
        )
    }
}
