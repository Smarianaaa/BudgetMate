package com.example.budgetmate.vistas.detalles_de_transaccion

import com.example.budgetmate.dominio.modelos.Transaccion

data class EstadoTransaccionActual(
    val transaccion: Transaccion? = Transaccion(
        id = -1,
        titulo = "",
        tipoTransaccion = "",
        cantidad = 0,
        etiquetas = "",
        nota = "",
        fecha = "",
        categoria = 0,
        cuenta = 0
    ),
)
