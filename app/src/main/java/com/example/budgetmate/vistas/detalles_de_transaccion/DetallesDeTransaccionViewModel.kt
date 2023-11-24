package com.example.budgetmate.vistas.detalles_de_transaccion

import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetmate.dominio.modelos.Categoria
import com.example.budgetmate.dominio.modelos.Cuenta
import com.example.budgetmate.dominio.repositorios.RepositorioCategoria
import com.example.budgetmate.dominio.repositorios.RepositorioCuenta
import com.example.budgetmate.dominio.repositorios.RepositorioTransaccion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetallesDeTransaccionViewModel @Inject constructor(
    private val repositorioTransaccion: RepositorioTransaccion,
    private val repositorioCategoria: RepositorioCategoria,
    private val repositorioCuenta: RepositorioCuenta,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val idDeTransaccion: Int = checkNotNull(savedStateHandle["idTransaccion"])
    private val _transaccionActual = mutableStateOf(EstadoTransaccionActual())
    val transaccionActual: State<EstadoTransaccionActual> = _transaccionActual

    private val _categoria = mutableStateOf(
        Categoria(
            id = 0,
            nombre = "",
            icono = "",
            color = ""
        )
    )
    val categoria: State<Categoria> = _categoria

    private val _cuenta = mutableStateOf(
        Cuenta(
            id = 0,
            nombre = "",
            icono = "",
            color = "",
            saldo = 0L,
            tipo = "",
        )
    )
    val cuenta: State<Cuenta> = _cuenta

    init {
        viewModelScope.launch {
            repositorioTransaccion.obtenerTransaccionPorId(idDeTransaccion).collect {
                _transaccionActual.value = transaccionActual.value.copy(
                    transaccion = it
                )
            }
        }
    }

    fun obtenerCategoriaPorId(id: Int) {
        viewModelScope.launch {
            repositorioCategoria.obtenerCategoriaPorId(id).collect {
                _categoria.value = it
            }
        }
    }

    fun obtenerCuentaPorId(id: Int) {
        viewModelScope.launch {
            repositorioCuenta.obtenerCuentaPorId(id).collect {
                _cuenta.value = it
            }
        }
    }

    fun onEvent(evento: EventoDetallesDeTransaccion) {
        when (evento) {
            is EventoDetallesDeTransaccion.Eliminar -> {
                evento.navHostController.navigateUp()
                viewModelScope.launch {
                    repositorioTransaccion.eliminarTransaccionPorId(evento.id)
                }
            }

            is EventoDetallesDeTransaccion.Editar -> {}
            is EventoDetallesDeTransaccion.Compartir -> {
                val enviarIntento: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        if (_transaccionActual.value.transaccion?.tipoTransaccion == "Gasto") {
                            "He pagado Q.${_transaccionActual.value.transaccion?.cantidad} por ${_transaccionActual.value.transaccion!!.titulo}."
                        } else {
                            "He ganado Q.${_transaccionActual.value.transaccion?.cantidad} con ${_transaccionActual.value.transaccion!!.titulo}."
                        }
                    )
                    type = "text/plain"
                }
                val compartirIntento = Intent.createChooser(enviarIntento, null)
                evento.contexto.startActivity(compartirIntento)
            }
        }
    }
}