package com.cora.challenge.shared.config

import org.springframework.http.converter.HttpMessageNotReadableException
import com.cora.challenge.shared.exceptions.ErrorResponse
import com.cora.challenge.shared.exceptions.FieldError
import com.cora.challenge.shared.exceptions.ValidationException
import com.cora.challenge.shared.interfaces.ApplicationException
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException::class)
    fun handleApplication(
        ex: ApplicationException,
        response: HttpServletResponse
    ): ErrorResponse {
        response.status = ex.statusCode
        return ErrorResponse(
            message = ex.message,
            statusCode = ex.statusCode
        )
    }

    @ExceptionHandler(ValidationException::class)
    fun handleValidation(
        ex: ValidationException,
        response: HttpServletResponse
    ): ErrorResponse {
        response.status = 400
        return ErrorResponse(
            message = ex.message,
            statusCode = 400,
            errors = ex.errors
        )
    }



    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleMessageNotReadable(
        ex: HttpMessageNotReadableException,
        response: HttpServletResponse
    ): ErrorResponse {
        response.status = 400
        return ErrorResponse(
            message = "Request body is missing or malformed",
            statusCode = 400
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        response: HttpServletResponse
    ): ErrorResponse {
        response.status = 400
        val fieldErrors = ex.bindingResult.fieldErrors.map {
            FieldError(
                field = it.field,
                message = it.defaultMessage ?: "invalid field"
            )
        }
        return ErrorResponse(
            message = "Validation failed",
            statusCode = 400,
            errors = fieldErrors
        )
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFound(
        ex: NoResourceFoundException,
        response: HttpServletResponse
    ): ErrorResponse {
        response.status = 404
        return ErrorResponse(
            message = "Route not found",
            statusCode = 404
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(
        ex: Exception,
        response: HttpServletResponse
    ): ErrorResponse {
        response.status = 500
        return ErrorResponse(
            message = "Internal server error",
            statusCode = 500
        )
    }
}