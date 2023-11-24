package com.example.budgetmate.vistas.transacciones

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetmate.dominio.repositorios.RepositorioTransaccion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransaccionesViewModel @Inject constructor(
    private val repositorioTransaccion: RepositorioTransaccion
) : ViewModel() {
    private val _transacciones = mutableStateOf(TransaccionesEstado())
    val transacciones: State<TransaccionesEstado> = _transacciones

    private val _tipoDeTransaccion = mutableStateOf(
        TransaccionesDropDownEstado(
            opcionSeleccionada = "Todas"
        )
    )
    val tipoDeTransaccion: State<TransaccionesDropDownEstado> = _tipoDeTransaccion

    init {
        viewModelScope.launch {
            repositorioTransaccion.obtenerTodasTransacciones().collect {
                _transacciones.value = transacciones.value.copy(
                    lista = it.reversed()
                )
            }
        }
    }

    fun onEvent(event: TransaccionesEvento) {
        when (event) {
            is TransaccionesEvento.EnCambioDeExpansion -> {
                _tipoDeTransaccion.value = _tipoDeTransaccion.value.copy(
                    estaExpandido = !_tipoDeTransaccion.value.estaExpandido
                )
            }

            is TransaccionesEvento.EnSolicitudDeDescarte -> {
                _tipoDeTransaccion.value = tipoDeTransaccion.value.copy(
                    estaExpandido = false
                )
            }

            is TransaccionesEvento.CambiarOpcionSeleccionada -> {
                _tipoDeTransaccion.value = tipoDeTransaccion.value.copy(
                    opcionSeleccionada = event.valor
                )
                viewModelScope.launch {
                    repositorioTransaccion.obtenerTodasTransacciones().collect {
                        _transacciones.value = transacciones.value.copy(
                            lista = when (_tipoDeTransaccion.value.opcionSeleccionada) {
                                "Gasto" -> {
                                    it.filter { transaccion ->
                                        transaccion.tipoTransaccion == "Gasto"
                                    }.reversed()
                                }

                                "Ingreso" -> {
                                    it.filter { transaccion ->
                                        transaccion.tipoTransaccion == "Ingreso"
                                    }.reversed()
                                }

                                else -> {
                                    it.reversed()
                                }
                            }
                        )
                    }
                }
            }

            else -> {}
        }
    }
}