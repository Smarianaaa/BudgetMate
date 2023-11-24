package com.example.budgetmate.vistas.agregar_editar_transaccion

import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetmate.dominio.modelos.Transaccion
import com.example.budgetmate.dominio.repositorios.RepositorioCategoria
import com.example.budgetmate.dominio.repositorios.RepositorioCuenta
import com.example.budgetmate.dominio.repositorios.RepositorioTransaccion
import com.example.budgetmate.util.EstadoDeDialogo
import com.example.budgetmate.util.EstadoDeDropDown
import com.example.budgetmate.util.Pantallas
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AgregarEditarTransaccionViewModel @Inject constructor(
    private val repositorioTransaccion: RepositorioTransaccion,
    private val repositorioCategoria: RepositorioCategoria,
    private val repositorioCuenta: RepositorioCuenta,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val idTransaccion: Int = checkNotNull(savedStateHandle["id"])
    private val pantallaAnterior: String = checkNotNull(savedStateHandle["pantallaAnterior"])

    private val _titulo = mutableStateOf(
        AgregarEditarTransaccionEstadoDeTexto(
            hint = "Ingrese un título.."
        )
    )
    val titulo: State<AgregarEditarTransaccionEstadoDeTexto> = _titulo

    private val _etiquetas = mutableStateOf(
        AgregarEditarTransaccionEstadoDeTexto(
            hint = "Etiquetas"
        )
    )
    val etiquetas: State<AgregarEditarTransaccionEstadoDeTexto> = _etiquetas

    private val _cantidad = mutableStateOf(
        AgregarEditarTransaccionEstadoDeTexto(
            hint = "Ingresa la cantidad.."
        )
    )
    val cantidad: State<AgregarEditarTransaccionEstadoDeTexto> = _cantidad

    private val _nota = mutableStateOf(
        AgregarEditarTransaccionEstadoDeTexto(
            hint = "Escribe una nota.."
        )
    )
    val nota: State<AgregarEditarTransaccionEstadoDeTexto> = _nota

    private val _tipoDeTransaccion = mutableStateOf(
        EstadoDeDropDown(
            hint = "Tipo de transacción"
        )
    )
    val tipoDeTransaccion: State<EstadoDeDropDown> = _tipoDeTransaccion

    private val _categorias = mutableStateOf(
        EstadoDeDropDown(
            hint = "Categoría"
        )
    )
    val categorias: State<EstadoDeDropDown> = _categorias

    private val _cuentas = mutableStateOf(
        EstadoDeDropDown(
            hint = "Cuenta"
        )
    )
    val cuentas: State<EstadoDeDropDown> = _cuentas

    private val _estadoDeDialogo = mutableStateOf(EstadoDeDialogo())
    val estadoDeDialogo: State<EstadoDeDialogo> = _estadoDeDialogo

    init {
        if (pantallaAnterior == Pantallas.DetallesDeTransaccion.ruta) {
            viewModelScope.launch {
                repositorioTransaccion.obtenerTransaccionPorId(idTransaccion).collect {
                    obtenerCuentaPorId(it.cuenta)
                    obtenerCategoriaPorId(it.categoria)
                    _titulo.value = titulo.value.copy(texto = it.titulo)
                    _etiquetas.value = etiquetas.value.copy(texto = it.etiquetas)
                    _cantidad.value = cantidad.value.copy(texto = it.cantidad.toString())
                    _nota.value = nota.value.copy(texto = it.nota)
                    _tipoDeTransaccion.value =
                        tipoDeTransaccion.value.copy(opcionSeleccionada = it.tipoTransaccion)
                    _estadoDeDialogo.value = estadoDeDialogo.value.copy(
                        texto = "Quieres descartar los cambios?"
                    )
                }
            }
        }
    }

    fun onEvent(evento: AgregarEditarTransaccionEvento) {
        when (evento) {
            is AgregarEditarTransaccionEvento.IngresarTitulo -> {
                _titulo.value = titulo.value.copy(
                    texto = evento.valor
                )
            }

            is AgregarEditarTransaccionEvento.IngresarEtiquetas -> {
                _etiquetas.value = etiquetas.value.copy(
                    texto = evento.valor
                )
            }

            is AgregarEditarTransaccionEvento.IngresarCantidad -> {
                _cantidad.value = cantidad.value.copy(
                    texto = evento.valor
                )
            }

            is AgregarEditarTransaccionEvento.IngresarNota -> {
                _nota.value = nota.value.copy(
                    texto = evento.valor
                )
            }

            is AgregarEditarTransaccionEvento.CambioExpandidoTipo -> {
                _tipoDeTransaccion.value = tipoDeTransaccion.value.copy(
                    estaExpandido = !_tipoDeTransaccion.value.estaExpandido
                )
            }

            is AgregarEditarTransaccionEvento.CambioExpandidoCategoria -> {
                _categorias.value = categorias.value.copy(
                    estaExpandido = !_categorias.value.estaExpandido
                )
            }

            is AgregarEditarTransaccionEvento.CambioExpandidoCuenta -> {
                _cuentas.value = cuentas.value.copy(
                    estaExpandido = !_cuentas.value.estaExpandido
                )
            }

            is AgregarEditarTransaccionEvento.DescartarTipo -> {
                _tipoDeTransaccion.value = tipoDeTransaccion.value.copy(
                    estaExpandido = false
                )
            }

            is AgregarEditarTransaccionEvento.DescartarCategoria -> {
                _categorias.value = categorias.value.copy(
                    estaExpandido = false
                )
            }

            is AgregarEditarTransaccionEvento.DescartarCuenta -> {
                _cuentas.value = cuentas.value.copy(
                    estaExpandido = false
                )
            }

            is AgregarEditarTransaccionEvento.CambiarTipo -> {
                _tipoDeTransaccion.value = tipoDeTransaccion.value.copy(
                    opcionSeleccionada = evento.valor,
                    estaExpandido = false
                )
            }

            is AgregarEditarTransaccionEvento.CambiarCategoria -> {
                _categorias.value = categorias.value.copy(
                    opcionSeleccionada = evento.valor.nombre,
                    categoria = evento.valor,
                    estaExpandido = false
                )
            }

            is AgregarEditarTransaccionEvento.CambiarCuenta -> {
                _cuentas.value = cuentas.value.copy(
                    opcionSeleccionada = evento.valor.nombre,
                    cuenta = evento.valor,
                    estaExpandido = false
                )
            }

            is AgregarEditarTransaccionEvento.AbrirDialogo -> {
                _estadoDeDialogo.value = estadoDeDialogo.value.copy(
                    estado = true
                )
            }

            is AgregarEditarTransaccionEvento.CerrarDialogo -> {
                _estadoDeDialogo.value = estadoDeDialogo.value.copy(
                    estado = false
                )
            }

            is AgregarEditarTransaccionEvento.GuardarEditarTransaccion -> {
                if (
                    _titulo.value.texto.isNotBlank() &&
                    _cantidad.value.texto.isNotBlank() &&
                    _tipoDeTransaccion.value.opcionSeleccionada.isNotBlank() &&
                    _categorias.value.categoria.id != 0 &&
                    _cuentas.value.cuenta.id != 0 &&
                    _etiquetas.value.texto.isNotBlank() &&
                    _nota.value.texto.isNotBlank()
                ) {
                    viewModelScope.launch {
                        if (pantallaAnterior == Pantallas.DetallesDeTransaccion.ruta) {
                            repositorioTransaccion.actualizar(
                                Transaccion(
                                    id = idTransaccion,
                                    titulo = _titulo.value.texto,
                                    etiquetas = _etiquetas.value.texto,
                                    cantidad = _cantidad.value.texto.toLong(),
                                    nota = _nota.value.texto,
                                    tipoTransaccion = _tipoDeTransaccion.value.opcionSeleccionada,
                                    cuenta = _cuentas.value.cuenta.id,
                                    categoria = _categorias.value.categoria.id,
                                    fecha = obtenerFecha()
                                )
                            )
                        } else {
                            repositorioTransaccion.insertar(
                                Transaccion(
                                    titulo = _titulo.value.texto,
                                    etiquetas = _etiquetas.value.texto,
                                    cantidad = _cantidad.value.texto.toLong(),
                                    nota = _nota.value.texto,
                                    tipoTransaccion = _tipoDeTransaccion.value.opcionSeleccionada,
                                    cuenta = _cuentas.value.cuenta.id,
                                    categoria = _categorias.value.categoria.id,
                                    fecha = obtenerFecha()
                                )
                            )
                        }
                    }
                    evento.navHostController.navigateUp()
                } else {
                    Toast.makeText(
                        evento.contexto,
                        "Por favor, rellene todos los campos.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun obtenerCategoriaPorId(idCategoria: Int) {
        viewModelScope.launch {
            repositorioCategoria.obtenerCategoriaPorId(idCategoria).collect {
                _categorias.value = categorias.value.copy(
                    opcionSeleccionada = it.nombre,
                    categoria = it
                )
            }
        }
    }

    private fun obtenerCuentaPorId(idCuenta: Int) {
        viewModelScope.launch {
            repositorioCuenta.obtenerCuentaPorId(idCuenta).collect {
                _cuentas.value = cuentas.value.copy(
                    opcionSeleccionada = it.nombre,
                    cuenta = it
                )
            }
        }
    }

    private fun obtenerFecha(): String {
        val currentDateTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("d MMM, yyyy, hh:mm a", Locale.getDefault())
        return formatter.format(currentDateTime)
    }
}