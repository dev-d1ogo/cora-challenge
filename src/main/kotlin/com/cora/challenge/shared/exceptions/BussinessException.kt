package com.cora.challenge.shared.exceptions

import com.cora.challenge.shared.interfaces.ApplicationException


class BussinessException(
    message: String,
    statusCode: Int
) : ApplicationException(message, statusCode)


