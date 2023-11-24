package com.example.budgetmate.vistas.detalles_de_transaccion

import android.content.Context
import androidx.navigation.NavHostController
import com.example.budgetmate.dominio.modelos.Transaccion

sealed class EventoDetallesDeTransaccion {
    data class Eliminar(val navHostController: NavHostController, val id: Int) : EventoDetallesDeTransaccion()
    data class Editar(val transaccion: Transaccion) : EventoDetallesDeTransaccion()
    data class Compartir(val contexto: Context) : EventoDetallesDeTransaccion()
}
