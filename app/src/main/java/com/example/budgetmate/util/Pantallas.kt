package com.example.budgetmate.util

sealed class Pantallas(val ruta: String) {
    object Dashboard : Pantallas(ruta = "dashboard")
    object DetallesDeTransaccion : Pantallas(ruta = "detalles_de_transaccion")
    object AgregarEditarTransaccion : Pantallas(ruta = "agregar_transaccion")
    object AcercaDe : Pantallas(ruta = "acerca_de")
    object Transacciones : Pantallas(ruta = "transacciones")
    object Cuentas : Pantallas(ruta = "cuentas")
    object AgregarEditarCuenta : Pantallas(ruta = "agregar_editar_cuenta")
    object Categorias : Pantallas(ruta = "categorias")
    object AgregarEditarCategoria : Pantallas(ruta = "agregar_editar_categoria")

    fun conArgumentos(vararg args: String): String {
        return buildString {
            append(ruta)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}