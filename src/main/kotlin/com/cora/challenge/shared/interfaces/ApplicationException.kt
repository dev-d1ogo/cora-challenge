package com.cora.challenge.shared.interfaces

open class ApplicationException (
    override val message: String,
    val statusCode: Int
):Exception(message)
