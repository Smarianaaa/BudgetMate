package com.example.budgetmate.huh.agregar_editar_cuenta

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.BusinessCenter
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetmate.dominio.modelos.Cuenta
import com.example.budgetmate.dominio.repositorios.RepositorioCuenta
import com.example.budgetmate.util.EstadoDeDialogo
import com.example.budgetmate.util.EstadoDeDropDown
import com.example.budgetmate.util.EstadoDeTexto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgregarEditarCuentaViewModel @Inject constructor(
    private val repositorioCuenta: RepositorioCuenta,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val tiposCuenta = listOf(
        "Efectivo",
        "Tarjeta de crédito",
        "Tarjeta de débito",
        "Cuenta bancaria",
        "Cuenta de ahorros"
    )

    val listaIconos = listOf(
        Icons.Outlined.AccountBalance,
        Icons.Outlined.Money,
        Icons.Outlined.Wallet,
        Icons.Outlined.CreditCard,
        Icons.Outlined.AccountBalanceWallet,
        Icons.Outlined.Business,
        Icons.Outlined.BusinessCenter
    )

    val listasColores = listOf(
        Color(0xFFE57373),
        Color(0xFFF06292),
        Color(0xFFBA68C8),
        Color(0xFF9575CD),
        Color(0xFF7986CB),
        Color(0xFF64B5F6),
        Color(0xFF4FC3F7),
        Color(0xFF4DD0E1),
        Color(0xFF4DB6AC),
        Color(0xFF81C784),
        Color(0xFFAED581),
        Color(0xFFFF8A65),
        Color(0xFFD4E157),
        Color(0xFFFFD54F),
        Color(0xFFFFB74D),
        Color(0xFFA1887F),
        Color(0xFF90A4AE)
    )

    fun obtenerIcono(icono: String): ImageVector {
        return listaIconos.find {
            it.name == icono
        }?.let {
            it
        }!!
    }

    fun convertirColor(color: Color): String {
        return "#" + Integer.toHexString(color.toArgb()).substring(2)
    }

    private val idCuenta: Int = checkNotNull(savedStateHandle["id"])
    private val pantallaAnterior: String = checkNotNull(savedStateHandle["pantallaAnterior"])

    private val _nombre = mutableStateOf(
        EstadoDeTexto(
            hint = "Ingrese un nombre.."
        )
    )
    val nombre: State<EstadoDeTexto> = _nombre

    private val _tipo = mutableStateOf(
        EstadoDeDropDown(
            hint = "Tipo de cuenta"
        )
    )
    val tipo: State<EstadoDeDropDown> = _tipo

    private val _saldo = mutableStateOf(
        EstadoDeTexto(
            hint = "Ingrese el saldo.."
        )
    )
    val saldo: State<EstadoDeTexto> = _saldo

    private val _icono = mutableStateOf(
        EstadoDeDropDown(
            hint = "Icono"
        )
    )
    val icono: State<EstadoDeDropDown> = _icono

    private val _color = mutableStateOf(
        EstadoDeDropDown(
            hint = "Color"
        )
    )
    val color: State<EstadoDeDropDown> = _color

    private val _estadoDeDialogo = mutableStateOf(EstadoDeDialogo())
    val estadoDeDialogo: State<EstadoDeDialogo> = _estadoDeDialogo

    init {
        if (pantallaAnterior == "EditarCuenta") {
            viewModelScope.launch {
                repositorioCuenta.obtenerCuentaPorId(idCuenta)
                    .collect {
                        _nombre.value = nombre.value.copy(
                            texto = it.nombre
                        )
                        _tipo.value = tipo.value.copy(
                            opcionSeleccionada = it.tipo
                        )
                        _saldo.value = saldo.value.copy(
                            texto = it.saldo.toString()
                        )
                        _icono.value = icono.value.copy(
                            opcionSeleccionada = it.icono
                        )
                        _color.value = color.value.copy(
                            opcionSeleccionada = it.color
                        )
                        _estadoDeDialogo.value = estadoDeDialogo.value.copy(
                            texto = "Quieres descartar la edición?"
                        )
                    }
            }
        }
    }

    fun onEvent(evento: AgregarEditarCuentaEvento) {
        when (evento) {
            is AgregarEditarCuentaEvento.IngresarNombre -> {
                _nombre.value = nombre.value.copy(
                    texto = evento.valor
                )
            }

            is AgregarEditarCuentaEvento.IngresarTipo -> {
                _tipo.value = tipo.value.copy(
                    opcionSeleccionada = evento.valor
                )
            }

            is AgregarEditarCuentaEvento.IngresarSaldo -> {
                _saldo.value = saldo.value.copy(
                    texto = evento.valor
                )
            }

            is AgregarEditarCuentaEvento.IngresarIcono -> {
                _icono.value = icono.value.copy(
                    opcionSeleccionada = evento.valor,
                    estaExpandido = false
                )
            }

            is AgregarEditarCuentaEvento.IngresarColor -> {
                _color.value = color.value.copy(
                    opcionSeleccionada = evento.valor,
                    estaExpandido = false
                )
            }

            is AgregarEditarCuentaEvento.AbrirDialogo -> {
                _estadoDeDialogo.value = estadoDeDialogo.value.copy(
                    estado = true
                )
            }

            is AgregarEditarCuentaEvento.CerrarDialogo -> {
                _estadoDeDialogo.value = estadoDeDialogo.value.copy(
                    estado = false
                )
            }

            is AgregarEditarCuentaEvento.TipoExpandido -> {
                _tipo.value = tipo.value.copy(
                    estaExpandido = !tipo.value.estaExpandido
                )
            }

            is AgregarEditarCuentaEvento.DescartarTipo -> {
                _tipo.value = tipo.value.copy(
                    estaExpandido = false
                )
            }

            is AgregarEditarCuentaEvento.IconoExpandido -> {
                _icono.value = icono.value.copy(
                    estaExpandido = !icono.value.estaExpandido
                )
            }

            is AgregarEditarCuentaEvento.DescartarIcono -> {
                _icono.value = icono.value.copy(
                    estaExpandido = false
                )
            }

            is AgregarEditarCuentaEvento.ColorExpandido -> {
                _color.value = color.value.copy(
                    estaExpandido = !color.value.estaExpandido
                )
            }

            is AgregarEditarCuentaEvento.DescartarColor -> {
                _color.value = color.value.copy(
                    estaExpandido = false
                )
            }

            is AgregarEditarCuentaEvento.GuardarEditarCuenta -> {
                val nombre = _nombre.value.texto
                val tipo = _tipo.value.opcionSeleccionada
                val saldo = _saldo.value.texto
                val icono = _icono.value.opcionSeleccionada
                val color = _color.value.opcionSeleccionada

                if (nombre.isBlank() || tipo.isBlank() || saldo.isBlank() || icono.isBlank() || color.isBlank()) {
                    Toast.makeText(
                        evento.contexto,
                        "Por favor, rellene todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                viewModelScope.launch {
                    if (pantallaAnterior == "EditarCuenta") {
                        val cuenta = Cuenta(
                            id = idCuenta,
                            nombre = nombre,
                            tipo = tipo,
                            saldo = saldo.toLong(),
                            icono = icono,
                            color = color
                        )
                        repositorioCuenta.actualizar(cuenta)
                    } else {
                        val cuenta = Cuenta(
                            nombre = nombre,
                            tipo = tipo,
                            saldo = saldo.toLong(),
                            icono = icono,
                            color = color
                        )
                        repositorioCuenta.insertar(cuenta)
                    }
                    evento.navHostController.navigateUp()
                }
            }

            is AgregarEditarCuentaEvento.EliminarCuenta -> {
                evento.navHostController.navigateUp()
                viewModelScope.launch {
                    repositorioCuenta.eliminarCuentaPorId(idCuenta)
                }
            }

            else -> {}
        }
    }

}