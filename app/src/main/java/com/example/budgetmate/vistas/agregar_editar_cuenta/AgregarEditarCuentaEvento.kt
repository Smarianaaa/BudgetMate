package com.example.budgetmate.huh.agregar_editar_cuenta

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

sealed class AgregarEditarCuentaEvento {
    data class IngresarNombre(val valor: String) : AgregarEditarCuentaEvento()
    data class IngresarTipo(val valor: String) : AgregarEditarCuentaEvento()
    data class IngresarSaldo(val valor: String) : AgregarEditarCuentaEvento()
    data class IngresarIcono(val valor: String) : AgregarEditarCuentaEvento()
    data class IngresarColor(val valor: String) : AgregarEditarCuentaEvento()

    object TipoExpandido : AgregarEditarCuentaEvento()
    object DescartarTipo : AgregarEditarCuentaEvento()
    object IconoExpandido : AgregarEditarCuentaEvento()
    object DescartarIcono : AgregarEditarCuentaEvento()
    object ColorExpandido : AgregarEditarCuentaEvento()
    object DescartarColor : AgregarEditarCuentaEvento()

    data class GuardarEditarCuenta(val contexto: Context, val navHostController: NavHostController) : AgregarEditarCuentaEvento()
    data class EliminarCuenta(val navHostController: NavHostController, val id: Int) : AgregarEditarCuentaEvento()

    object AbrirDialogo : AgregarEditarCuentaEvento()
    object CerrarDialogo : AgregarEditarCuentaEvento()
}
