package com.example.budgetmate.vistas.agregar_editar_transaccion

import android.content.Context
import androidx.navigation.NavHostController
import com.example.budgetmate.dominio.modelos.Categoria
import com.example.budgetmate.dominio.modelos.Cuenta

sealed class AgregarEditarTransaccionEvento {
    data class IngresarTitulo(val valor: String) : AgregarEditarTransaccionEvento()
    data class IngresarCantidad(val valor: String) : AgregarEditarTransaccionEvento()
    data class IngresarNota(val valor: String) : AgregarEditarTransaccionEvento()
    data class IngresarEtiquetas(val valor: String) : AgregarEditarTransaccionEvento()

    data class GuardarEditarTransaccion(val contexto: Context, val navHostController: NavHostController) : AgregarEditarTransaccionEvento()

    object CambioExpandidoTipo : AgregarEditarTransaccionEvento()
    object CambioExpandidoCategoria : AgregarEditarTransaccionEvento()
    object CambioExpandidoCuenta : AgregarEditarTransaccionEvento()

    object DescartarTipo : AgregarEditarTransaccionEvento()
    object DescartarCategoria : AgregarEditarTransaccionEvento()
    object DescartarCuenta : AgregarEditarTransaccionEvento()

    data class CambiarCategoria(val valor: Categoria) : AgregarEditarTransaccionEvento()
    data class CambiarCuenta(val valor: Cuenta) : AgregarEditarTransaccionEvento()
    data class CambiarTipo(val valor: String) : AgregarEditarTransaccionEvento()

    object AbrirDialogo : AgregarEditarTransaccionEvento()
    object CerrarDialogo : AgregarEditarTransaccionEvento()
}
