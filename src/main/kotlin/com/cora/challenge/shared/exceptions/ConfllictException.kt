package com.cora.challenge.shared.exceptions

import com.cora.challenge.shared.interfaces.ApplicationException

class ConfllictException(
    message: String
) : ApplicationException(
    message, statusCode = 409
)