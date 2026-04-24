package com.cora.challenge.features.accounts.domain.useCases

import com.cora.challenge.features.accounts.domain.entities.Account
import com.cora.challenge.features.accounts.domain.repository.AccountRepository
import com.cora.challenge.features.accounts.domain.useCases.DTO.CreateAccountInput
import com.cora.challenge.features.accounts.domain.useCases.DTO.CreateAccountOutput
import com.cora.challenge.shared.exceptions.ConfllictException
import com.cora.challenge.shared.interfaces.UseCase
import org.springframework.stereotype.Component

@Component
class CreateAccountUseCase(private val accountRepository: AccountRepository) :
    UseCase<CreateAccountInput, CreateAccountOutput> {

    override fun execute(input: CreateAccountInput): CreateAccountOutput {
        val alreadyExistsAccount =
            accountRepository.findByCpf(input.cpf).getOrNull()

        alreadyExistsAccount?.let { throw ConfllictException("Account already exists") }

        val account = Account.create(
            input.name,
            input.cpf
        )

        accountRepository.save(account)

        return CreateAccountOutput(
            "Account created successfully",
            true
        )
    }
}