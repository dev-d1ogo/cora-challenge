package com.cora.challenge.features.accounts.presentation

import com.cora.challenge.features.accounts.domain.entities.Account
import com.cora.challenge.features.accounts.domain.useCases.CreateAccountUseCase
import com.cora.challenge.features.accounts.domain.useCases.DTO.CreateAccountInput
import com.cora.challenge.features.accounts.domain.useCases.DTO.CreateAccountOutput
import com.cora.challenge.features.accounts.domain.useCases.GetAccountsUseCase
import com.cora.challenge.features.accounts.presentation.DTO.AccountRequestDTO
import com.cora.challenge.shared.interfaces.execute
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(
    private val createAccountUseCase: CreateAccountUseCase,
    private val getAccountUseCase: GetAccountsUseCase,
) {
    @PostMapping
    fun createAccount(@Valid @RequestBody accountRequestDTO: AccountRequestDTO): ResponseEntity<CreateAccountOutput> {
            val input = CreateAccountInput(
                name = accountRequestDTO.name.toString(),
                cpf = accountRequestDTO.cpf.toString()
            )

            val output = createAccountUseCase.execute(input)

            return ResponseEntity.status(HttpStatus.CREATED).body(output)
    }

    @GetMapping
    fun listAccounts(): ResponseEntity<List<Account>> {
        val accounts = getAccountUseCase.execute()

        return ResponseEntity.ok(accounts)
    }
}