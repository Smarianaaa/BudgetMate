package com.example.budgetmate.util

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.budgetmate.componentes.BarraInferior
import com.example.budgetmate.vistas.acerca_de.AcercaDe
import com.example.budgetmate.vistas.agregar_editar_categoria.AgregarEditarCategoria
import com.example.budgetmate.vistas.agregar_editar_cuenta.AgregarEditarCuenta
import com.example.budgetmate.vistas.agregar_editar_transaccion.AgregarEditarTransaccion
import com.example.budgetmate.vistas.categorias.Categorias
import com.example.budgetmate.vistas.cuentas.Cuentas
import com.example.budgetmate.vistas.dashboard.Dashboard
import com.example.budgetmate.vistas.detalles_de_transaccion.DetallesDeTransaccion
import com.example.budgetmate.vistas.transacciones.Transacciones

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun IniciarNavegacion() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        floatingActionButton = {
            if (currentRoute == Pantallas.Dashboard.ruta || currentRoute == Pantallas.Transacciones.ruta) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Pantallas.AgregarEditarTransaccion.ruta + "/-1" + "/agregar")
                    }, containerColor = MaterialTheme.colorScheme.surfaceTint
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = null,
                    )
                }
            }
        },
        bottomBar = {
            if (currentRoute == Pantallas.Dashboard.ruta || currentRoute == Pantallas.Transacciones.ruta) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    BarraInferior(navController = navController)
                }
            }
        },

        ) {
        NavHost(navController = navController, startDestination = Pantallas.Dashboard.ruta) {

            composable(
                route = Pantallas.Dashboard.ruta,
            ) {
                Dashboard(navController)
            }

            composable(
                route = Pantallas.AgregarEditarTransaccion.ruta + "/{id}/{pantallaAnterior}",
                arguments = listOf(
                    navArgument(
                        name = "id"
                    ) {
                        type = NavType.IntType
                        defaultValue = -1
                    },
                    navArgument(
                        name = "pantallaAnterior"
                    ) {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) {
                val idTransaccion = it.arguments?.getInt("id") ?: -1
                val pantallaAnterior = it.arguments?.getString("pantallaAnterior") ?: ""
                AgregarEditarTransaccion(
                    navHostController = navController,
                    idTransaccion = idTransaccion,
                    pantallaAnterior = pantallaAnterior
                )
            }

            composable(
                route = Pantallas.AgregarEditarCuenta.ruta + "/{id}/{pantallaAnterior}",
                arguments = listOf(
                    navArgument(
                        name = "id"
                    ) {
                        type = NavType.IntType
                        defaultValue = -1
                    },
                    navArgument(
                        name = "pantallaAnterior"
                    ) {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) {
                val idCuenta = it.arguments?.getInt("id") ?: -1
                val pantallaAnterior = it.arguments?.getString("pantallaAnterior") ?: ""
                AgregarEditarCuenta(
                    navHostController = navController,
                    idCuenta = idCuenta,
                    pantallaAnterior = pantallaAnterior
                )
            }

            composable(
                route = Pantallas.Cuentas.ruta
            ) {
                Cuentas(navHostController = navController)
            }

            composable(
                route = Pantallas.DetallesDeTransaccion.ruta + "/{idTransaccion}",
                arguments = listOf(
                    navArgument(
                        name = "idTransaccion"
                    ) {
                        type = NavType.IntType
                        defaultValue = -1
                    })
            ) {
                val idTransaccion = it.arguments?.getInt("idTransaccion") ?: -1
                DetallesDeTransaccion(
                    navHostController = navController,
                    idTransaccion = idTransaccion
                )
            }

            composable(
                route = Pantallas.Transacciones.ruta
            ) {
                Transacciones(navHostController = navController)
            }

            composable(
                route = Pantallas.AgregarEditarCategoria.ruta + "/{id}/{pantallaAnterior}",
                arguments = listOf(
                    navArgument(
                        name = "id"
                    ) {
                        type = NavType.IntType
                        defaultValue = -1
                    },
                    navArgument(
                        name = "pantallaAnterior"
                    ) {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) {
                val idCategoria = it.arguments?.getInt("id") ?: -1
                val pantallaAnterior = it.arguments?.getString("pantallaAnterior") ?: ""
                AgregarEditarCategoria(
                    navHostController = navController,
                    idCuenta = idCategoria,
                    pantallaAnterior = pantallaAnterior
                )
            }

            composable(
                route = Pantallas.Categorias.ruta
            ) {
                Categorias(navHostController = navController)
            }

            composable(route = Pantallas.AcercaDe.ruta) {
                AcercaDe()
            }

        }
    }
}