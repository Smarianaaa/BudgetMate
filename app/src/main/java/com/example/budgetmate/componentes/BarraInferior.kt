package com.example.budgetmate.componentes

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.budgetmate.R
import com.example.budgetmate.util.Pantallas

@Composable
fun BarraInferior(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        tonalElevation = 8.dp,
    ) {
        NavigationBarItem(
            icon =
            {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = stringResource(R.string.dashboard),
                )
            },
            label = {
                Text(text = "Dashboard")
            },
            alwaysShowLabel = false,
            selected = currentRoute == Pantallas.Dashboard.ruta,
            onClick = {
                navController.navigate(Pantallas.Dashboard.ruta) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon =
            {
                Icon(
                    imageVector = Icons.Filled.Assignment,
                    contentDescription = stringResource(R.string.transacciones),
                )
            },
            label = {
                Text(text = "Transacciones")
            },
            alwaysShowLabel = false,
            selected = currentRoute == Pantallas.Transacciones.ruta,
            onClick = {
                navController.navigate(Pantallas.Transacciones.ruta) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Category,
                    contentDescription = null,
                )
            },
            label = {
                Text(text = "Categorías")
            },
            alwaysShowLabel = false,
            selected = currentRoute == Pantallas.Categorias.ruta,
            onClick = {
                navController.navigate(Pantallas.Categorias.ruta) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

    }

}