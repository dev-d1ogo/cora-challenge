package com.cora.challenge.shared.exceptions

import com.cora.challenge.shared.interfaces.ApplicationException

class NotFoundException(message: String) : ApplicationException(
    message,
    404
)