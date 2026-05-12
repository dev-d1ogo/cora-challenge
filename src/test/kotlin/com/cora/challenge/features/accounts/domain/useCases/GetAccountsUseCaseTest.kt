package com.cora.challenge.features.accounts.domain.useCases

import com.cora.challenge.features.accounts.domain.entities.Account
import com.cora.challenge.features.accounts.domain.repository.AccountRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.util.Date
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Tag("unit")
class GetAccountsUseCaseTest {

    private val accountRepository = mockk<AccountRepository>()
    private lateinit var useCase: GetAccountsUseCase

    @BeforeEach
    fun setup() {
        useCase = GetAccountsUseCase(accountRepository)
    }

    @Test
    fun `should return list of accounts`() {
        val accounts = listOf(
            Account(name = "João Silva", cpf = "12345678901", createdAt = Date()),
            Account(name = "Maria Souza", cpf = "98765432100", createdAt = Date())
        )

        every { accountRepository.findAll() } returns Result.success(accounts)

        val result = useCase.execute(Unit)

        assertEquals(2, result.size)
        assertEquals("12345678901", result[0].cpf)
    }

    @Test
    fun `should return empty list when no accounts exist`() {
        every { accountRepository.findAll() } returns Result.success(emptyList())

        val result = useCase.execute(Unit)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `should return empty list when repository fails`() {
        every { accountRepository.findAll() } returns Result.failure(RuntimeException("DB error"))

        val result = useCase.execute(Unit)

        assertTrue(result.isEmpty())
    }
}
