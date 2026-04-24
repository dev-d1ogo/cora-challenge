package com.cora.challenge.shared.exceptions

import com.cora.challenge.shared.interfaces.ApplicationException

class ValidationException(val errors: List<FieldError>) : ApplicationException(
    message="Validation Error",
    statusCode = 400
)
