package com.cora.challenge.features.accounts.domain.useCases

import com.cora.challenge.features.accounts.domain.entities.Account
import com.cora.challenge.features.accounts.domain.repository.AccountRepository
import com.cora.challenge.shared.interfaces.UseCase
import jakarta.validation.constraints.Null
import org.springframework.stereotype.Component

@Component
class GetAccountsUseCase(private val accountRepository: AccountRepository) :
    UseCase<Unit, List<Account>> {
    override fun execute(input: Unit): List<Account> {
        val accounts = accountRepository.findAll()

        return accounts.getOrNull() ?: emptyList()
    }
}