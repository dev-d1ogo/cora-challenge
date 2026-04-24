package com.cora.challenge.shared.exceptions

import com.cora.challenge.shared.interfaces.ApplicationException

class InfraException(message: String) : ApplicationException(
    message,
    statusCode = 502
)