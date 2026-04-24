package com.cora.challenge.features.accounts.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
 interface AccountJpaRepository : JpaRepository<AccountEntity, UUID>{
     fun findByCpf(cpf: String): AccountEntity?
 }
