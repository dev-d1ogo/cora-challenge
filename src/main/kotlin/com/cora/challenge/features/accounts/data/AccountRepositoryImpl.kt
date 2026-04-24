package com.cora.challenge.features.accounts.data

import com.cora.challenge.features.accounts.AccountMapper
import com.cora.challenge.features.accounts.domain.entities.Account
import com.cora.challenge.features.accounts.domain.repository.AccountRepository
import com.cora.challenge.shared.exceptions.InfraException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class AccountRepositoryImpl(
    private val accountJpaRepository: AccountJpaRepository,
) : AccountRepository {

    val logger: Logger = LoggerFactory.getLogger(AccountRepositoryImpl::class.java)

    private fun mapToDomain(entity: AccountEntity) =
        AccountMapper.toDomainFromDatabase(entity)

    override fun save(account: Account): Result<Account> = runCatching {
        val entity = AccountMapper.toEntity(account)
        accountJpaRepository.save(entity)
    }.onFailure {
        logger.error(
            it.message,
            it
        )
        throw it
    }.map { mapToDomain(it) }

    override fun findByCpf(cpf: String): Result<Account?> = runCatching {
        val entity = accountJpaRepository.findByCpf(cpf)
        entity?.let { mapToDomain(it) }
    }.onFailure {
        logger.error(
            it.message,
            it
        )
        throw InfraException("Error finding account")
    }

    override fun findAll(): Result<List<Account>> = runCatching {
        val entities = accountJpaRepository.findAll()
        entities.map { mapToDomain(it) }
    }.onFailure {
        logger.error(
            it.message,
            it
        )
        throw InfraException("Error finding accounts")
    }
}