package com.example.budgetmate.vistas.transacciones

import com.example.budgetmate.dominio.modelos.Transaccion

data class TransaccionesEstado (
    val lista: List<Transaccion> = mutableListOf()
)