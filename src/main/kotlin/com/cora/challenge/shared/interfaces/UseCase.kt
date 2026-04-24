package com.cora.challenge.shared.interfaces

interface UseCase<Input, Output> {
    fun execute(input: Input): Output
}

// extension que permite chamar sem parâmetro quando Input = Unit
fun <Output> UseCase<Unit, Output>.execute() = execute(Unit)