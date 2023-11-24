package com.example.budgetmate.huh.agregar_editar_cuenta

import com.example.budgetmate.dominio.modelos.Cuenta

data class ListadoDeCuentas(
    val listado: List<Cuenta> = mutableListOf()
)
