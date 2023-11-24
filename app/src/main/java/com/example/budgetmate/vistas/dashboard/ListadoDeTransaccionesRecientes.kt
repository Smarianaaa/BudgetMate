package com.example.budgetmate.vistas.dashboard

import com.example.budgetmate.dominio.modelos.Transaccion

data class ListadoDeTransaccionesRecientes(
    val listado : List<Transaccion> = mutableListOf()
)
