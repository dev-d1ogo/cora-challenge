package com.cora.challenge.shared.exceptions

import java.time.LocalDateTime

data class ErrorResponse(
    val message: String,
    val statusCode: Int,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val errors: List<FieldError>? = null
)

data class FieldError(
    val message: String,
    val field: String
)