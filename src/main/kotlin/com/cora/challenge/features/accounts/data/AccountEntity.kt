package com.cora.challenge.features.accounts.data

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import org.springframework.data.domain.Persistable
import java.util.*

@Entity
@Table(name = "accounts")
class AccountEntity(
    @Id
    private val id: UUID,

    @Column(nullable = false)
    val name: String,

    @Column(
        nullable = false,
        unique = true
    )
    val cpf: String,

    @Column(
        nullable = false,
        updatable = false
    )
    val createdAt: Date = Date()
) : Persistable<UUID> {

    @Version
    var version: Long? = null

    override fun getId(): UUID = id

    override fun isNew(): Boolean = version == null
}