package com.example.budgetmate.vistas.transacciones

sealed class TransaccionesEvento {
    object EnCambioDeExpansion : TransaccionesEvento()
    object EnSolicitudDeDescarte : TransaccionesEvento()
    data class CambiarOpcionSeleccionada(val valor: String) : TransaccionesEvento()
}
