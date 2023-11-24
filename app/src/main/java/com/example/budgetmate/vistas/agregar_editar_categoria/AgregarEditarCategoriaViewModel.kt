package com.example.budgetmate.vistas.agregar_editar_categoria

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.AddLocationAlt
import androidx.compose.material.icons.outlined.CarCrash
import androidx.compose.material.icons.outlined.CardTravel
import androidx.compose.material.icons.outlined.ElectricalServices
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.FoodBank
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Train
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetmate.dominio.modelos.Categoria
import com.example.budgetmate.dominio.repositorios.RepositorioCategoria
import com.example.budgetmate.util.EstadoDeDialogo
import com.example.budgetmate.util.EstadoDeDropDown
import com.example.budgetmate.util.EstadoDeTexto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgregarEditarCategoriaViewModel @Inject constructor(
    private val repositorioCategoria: RepositorioCategoria,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val listaIconos = listOf(
        Icons.Outlined.Fastfood,
        Icons.Outlined.ElectricalServices,
        Icons.Outlined.Train,
        Icons.Outlined.Home,
        Icons.Outlined.AddLocationAlt,
        Icons.Outlined.LocalHospital,
        Icons.Outlined.AccountBalanceWallet,
        Icons.Outlined.CarCrash,
        Icons.Outlined.ShoppingCart,
        Icons.Outlined.FoodBank,
        Icons.Outlined.School,
        Icons.Outlined.CardTravel
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
        Color(0xFFFFF176),
        Color(0xFFFFD54F),
        Color(0xFFFFB74D),
        Color(0xFFFF8A65),
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

    private val idCategoria: Int = checkNotNull(savedStateHandle["id"])
    private val pantallaAnterior: String = checkNotNull(savedStateHandle["pantallaAnterior"])

    private val _nombre = mutableStateOf(
        EstadoDeTexto(
            hint = "Ingrese un nombre para la categoria.."
        )
    )
    val nombre: State<EstadoDeTexto> = _nombre

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
        if (pantallaAnterior == "EditarCategoria") {
            viewModelScope.launch {
                repositorioCategoria.obtenerCategoriaPorId(idCategoria).collect {
                    _nombre.value = _nombre.value.copy(
                        texto = it.nombre
                    )
                    _icono.value = _icono.value.copy(
                        opcionSeleccionada = it.icono
                    )
                    _color.value = _color.value.copy(
                        opcionSeleccionada = it.color
                    )
                }
            }
        }
    }

    fun onEvent(evento: AgregarEditarCategoriaEvento) {
        when (evento) {
            is AgregarEditarCategoriaEvento.AbrirDialogo -> {
                _estadoDeDialogo.value = estadoDeDialogo.value.copy(
                    estado = true
                )
            }

            is AgregarEditarCategoriaEvento.CerrarDialogo -> {
                _estadoDeDialogo.value = estadoDeDialogo.value.copy(
                    estado = false
                )
            }

            is AgregarEditarCategoriaEvento.IngresarNombre -> {
                _nombre.value = nombre.value.copy(
                    texto = evento.valor
                )
            }

            is AgregarEditarCategoriaEvento.IngresarIcono -> {
                _icono.value = icono.value.copy(
                    opcionSeleccionada = evento.valor,
                    estaExpandido = false
                )
            }

            is AgregarEditarCategoriaEvento.IngresarColor -> {
                _color.value = color.value.copy(
                    opcionSeleccionada = evento.valor,
                    estaExpandido = false
                )
            }

            is AgregarEditarCategoriaEvento.IconoExpandido -> {
                _icono.value = icono.value.copy(
                    estaExpandido = true
                )
            }

            is AgregarEditarCategoriaEvento.ColorExpandido -> {
                _color.value = color.value.copy(
                    estaExpandido = true
                )
            }

            is AgregarEditarCategoriaEvento.DescartarIcono -> {
                _icono.value = icono.value.copy(
                    estaExpandido = false
                )
            }

            is AgregarEditarCategoriaEvento.DescartarColor -> {
                _color.value = color.value.copy(
                    estaExpandido = false
                )
            }

            is AgregarEditarCategoriaEvento.GuardarEditarCategoria -> {
                val nombre = nombre.value.texto
                val icono = icono.value.opcionSeleccionada
                val color = color.value.opcionSeleccionada

                if (nombre.isBlank() ||
                    icono.isBlank() ||
                    color.isBlank() ||
                    icono == null ||
                    color == null
                ) {
                    Toast.makeText(
                        evento.contexto,
                        "Por favor, complete todos los campos.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                viewModelScope.launch {
                    if (pantallaAnterior == "EditarCategoria") {
                        repositorioCategoria.actualizar(
                            Categoria(
                                id = idCategoria,
                                nombre = nombre,
                                icono = icono,
                                color = color
                            )
                        )
                    } else {
                        repositorioCategoria.insertar(
                            Categoria(
                                nombre = nombre,
                                icono = icono,
                                color = color
                            )
                        )
                    }
                    evento.navHostController.navigateUp()
                }
            }
            else -> {}
        }
    }

}