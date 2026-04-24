package com.cora.challenge.features.accounts

import com.cora.challenge.features.accounts.data.AccountEntity
import com.cora.challenge.features.accounts.domain.entities.Account
import com.cora.challenge.features.accounts.presentation.DTO.AccountRequestDTO
import com.cora.challenge.shared.exceptions.FieldError
import com.cora.challenge.shared.exceptions.ValidationException
import java.util.*

object AccountMapper {
    fun toDomainFromDatabase(entity: AccountEntity) = Account(
        id = entity.getId().toString(),
        name = entity.name,
        cpf = entity.cpf,
        createdAt = entity.createdAt
    )

    fun toEntity(domain: Account) = AccountEntity(
        id = UUID.fromString(domain.id),
        cpf = domain.cpf,
        name = domain.name,
        createdAt = domain.createdAt
    )

}
