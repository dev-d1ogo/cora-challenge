package com.cora.challenge.features.accounts.domain.useCases

import com.cora.challenge.features.accounts.domain.entities.Account
import com.cora.challenge.features.accounts.domain.repository.AccountRepository
import com.cora.challenge.features.accounts.domain.useCases.DTO.CreateAccountInput
import com.cora.challenge.shared.exceptions.ConfllictException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Tag("unit")
class CreateAccountUseCaseTest {
    private val accountRepository = mockk<AccountRepository>()
    private lateinit var useCase: CreateAccountUseCase

    @BeforeEach
    fun setup() {
        useCase = CreateAccountUseCase(accountRepository)
    }

    @Test
    fun `should create account successfully when CPF does not exist`() {
        val input = CreateAccountInput(name = "João Silva", cpf = "12345678901")
        val account = Account.create(name = input.name, cpf = input.cpf)

        every { accountRepository.findByCpf(input.cpf) } returns Result.success(null)
        every { accountRepository.save(any()) } returns Result.success(account)

        val output = useCase.execute(input)

        assertTrue(output.success)
        assertEquals("Account created successfully", output.message)
        verify(exactly = 1) { accountRepository.save(any()) }
    }

    @Test
    fun `should throw ConflictException when CPF already exists`() {
        val input = CreateAccountInput(name = "João Silva", cpf = "12345678901")
        val existingAccount = Account(name = input.name, cpf = input.cpf, createdAt = Date())

        every { accountRepository.findByCpf(input.cpf) } returns Result.success(existingAccount)

        assertThrows<ConfllictException> { useCase.execute(input) }

        verify(exactly = 0) { accountRepository.save(any()) }
    }
}
