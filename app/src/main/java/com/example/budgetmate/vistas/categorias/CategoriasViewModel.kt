package com.example.budgetmate.vistas.categorias

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetmate.dominio.modelos.Categoria
import com.example.budgetmate.dominio.repositorios.RepositorioCategoria
import com.example.budgetmate.vistas.agregar_editar_categoria.ListadoDeCategorias
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriasViewModel @Inject constructor(
    private val repositorioCategoria: RepositorioCategoria
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

    fun obtenerIcono(icono: String): ImageVector? {
        return listaIconos.find { it.name == icono }
    }

    private val _categorias = mutableStateOf(ListadoDeCategorias())
    val categorias: State<ListadoDeCategorias> = _categorias

    init {
        viewModelScope.launch {
            repositorioCategoria.obtenerTodasCategorias().collect { categorias ->
                _categorias.value = ListadoDeCategorias(
                    listado = categorias
                )
            }
        }
    }

    fun obtenerCategoriaPorId(id: Int): State<Categoria> {
        val _categoria = mutableStateOf(
            Categoria(
                id = 0,
                nombre = "",
                icono = "",
                color = ""
            )
        )
        val categoria: State<Categoria> = _categoria
        viewModelScope.launch {
            repositorioCategoria.obtenerCategoriaPorId(id).collect { categoriaObtenida ->
                _categoria.value = categoria.value.copy(
                    id = categoriaObtenida.id,
                    nombre = categoriaObtenida.nombre,
                    icono = categoriaObtenida.icono
                )
            }
        }
        return categoria
    }

    fun eliminarCategoria(id: Int) {
        viewModelScope.launch {
            repositorioCategoria.eliminarCategoriaPorId(id)
        }
    }
}