package com.example.budgetmate.huh.cuentas

data class EstadoDeTarjetaCuenta (
    val balanceTotal: Long? = 0,
    val ingreso : Long? = 0L,
    val gasto : Long? = 0L,
)