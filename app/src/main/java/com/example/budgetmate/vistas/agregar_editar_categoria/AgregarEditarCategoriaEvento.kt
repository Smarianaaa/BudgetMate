package com.example.budgetmate.vistas.agregar_editar_categoria

import android.content.Context
import androidx.navigation.NavHostController

sealed class AgregarEditarCategoriaEvento {
    data class IngresarNombre(val valor: String) : AgregarEditarCategoriaEvento()
    data class IngresarIcono(val valor: String) : AgregarEditarCategoriaEvento()
    data class IngresarColor(val valor: String) : AgregarEditarCategoriaEvento()

    object TipoExpandido : AgregarEditarCategoriaEvento()
    object IconoExpandido : AgregarEditarCategoriaEvento()
    object ColorExpandido : AgregarEditarCategoriaEvento()

    object DescartarTipo : AgregarEditarCategoriaEvento()
    object DescartarIcono : AgregarEditarCategoriaEvento()
    object DescartarColor : AgregarEditarCategoriaEvento()

    data class GuardarEditarCategoria(val contexto: Context, val navHostController: NavHostController) : AgregarEditarCategoriaEvento()

    object AbrirDialogo : AgregarEditarCategoriaEvento()
    object CerrarDialogo : AgregarEditarCategoriaEvento()
}
