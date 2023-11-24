package com.example.budgetmate.util

import com.example.budgetmate.dominio.modelos.Categoria
import com.example.budgetmate.dominio.modelos.Cuenta

data class EstadoDeDropDown (
    val estaExpandido: Boolean = false,
    val opcionSeleccionada: String = "",
    val cuenta: Cuenta = Cuenta(
        id = 0,
        nombre = "",
        saldo = 0L,
        color = "#000000",
        icono = "",
        tipo = "",
    ),
    val categoria: Categoria = Categoria(
        id = 0,
        nombre = "",
        color = "#000000",
        icono = "",
    ),
    val hint: String = "",
)