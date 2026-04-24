package com.cora.challenge.features.accounts.domain.repository

import com.cora.challenge.features.accounts.domain.entities.Account



interface AccountRepository {
    fun findByCpf(cpf: String): Result<Account?>
    fun save(account: Account): Result<Account>
    fun findAll(): Result<List<Account>>
}
