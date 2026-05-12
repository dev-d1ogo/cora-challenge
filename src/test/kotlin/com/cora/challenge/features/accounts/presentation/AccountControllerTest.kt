package com.cora.challenge.features.accounts.presentation

import com.cora.challenge.features.accounts.domain.entities.Account
import com.cora.challenge.features.accounts.domain.useCases.CreateAccountUseCase
import com.cora.challenge.features.accounts.domain.useCases.DTO.CreateAccountInput
import com.cora.challenge.features.accounts.domain.useCases.DTO.CreateAccountOutput
import com.cora.challenge.features.accounts.domain.useCases.GetAccountsUseCase
import com.cora.challenge.shared.exceptions.ConfllictException
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.util.Date
import org.junit.jupiter.api.Tag

@Tag("controller-integration")
@WebMvcTest(AccountController::class)
class AccountControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var createAccountUseCase: CreateAccountUseCase

    @MockkBean
    private lateinit var getAccountsUseCase: GetAccountsUseCase

    @Test
    fun `POST accounts - should return 201 when account is created successfully`() {
        every { createAccountUseCase.execute(any<CreateAccountInput>()) } returns
                CreateAccountOutput("Account created successfully", true)

        mockMvc.post("/api/v1/accounts") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"name": "João Silva", "cpf": "12345678901"}"""
        }.andExpect {
            status { isCreated() }
            jsonPath("$.success") { value(true) }
            jsonPath("$.message") { value("Account created successfully") }
        }
    }

    @Test
    fun `POST accounts - should return 409 when CPF already exists`() {
        every { createAccountUseCase.execute(any<CreateAccountInput>()) } throws
                ConfllictException("Account already exists")

        mockMvc.post("/api/v1/accounts") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"name": "João Silva", "cpf": "12345678901"}"""
        }.andExpect {
            status { isConflict() }
            jsonPath("$.message") { value("Account already exists") }
        }
    }

    @Test
    fun `POST accounts - should return 400 when CPF has invalid format`() {
        mockMvc.post("/api/v1/accounts") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"name": "João Silva", "cpf": "123"}"""
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.errors[0].field") { value("cpf") }
        }
    }

    @Test
    fun `POST accounts - should return 400 when name is blank`() {
        mockMvc.post("/api/v1/accounts") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"name": "", "cpf": "12345678901"}"""
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.errors[0].field") { value("name") }
        }
    }

    @Test
    fun `POST accounts - should return 400 when body is malformed`() {
        mockMvc.post("/api/v1/accounts") {
            contentType = MediaType.APPLICATION_JSON
            content = """invalid json"""
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.message") { value("Request body is missing or malformed") }
        }
    }

    @Test
    fun `GET accounts - should return 200 with list of accounts`() {
        val accounts = listOf(
            Account(name = "João Silva", cpf = "12345678901", createdAt = Date()),
            Account(name = "Maria Souza", cpf = "98765432100", createdAt = Date())
        )

        every { getAccountsUseCase.execute(Unit) } returns accounts

        mockMvc.get("/api/v1/accounts").andExpect {
            status { isOk() }
            jsonPath("$.length()") { value(2) }
            jsonPath("$[0].cpf") { value("12345678901") }
        }
    }

    @Test
    fun `GET accounts - should return 200 with empty list`() {
        every { getAccountsUseCase.execute(Unit) } returns emptyList()

        mockMvc.get("/api/v1/accounts").andExpect {
            status { isOk() }
            jsonPath("$.length()") { value(0) }
        }
    }
}
