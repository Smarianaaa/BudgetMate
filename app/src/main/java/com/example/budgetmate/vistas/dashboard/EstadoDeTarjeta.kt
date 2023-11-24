package com.example.budgetmate.vistas.dashboard

data class EstadoDeTarjeta (
    val balanceTotal: Long? = 0,
    val ingreso : Long? = 0L,
    val gasto : Long? = 0L,
)