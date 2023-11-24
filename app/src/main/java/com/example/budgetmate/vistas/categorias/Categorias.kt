package com.example.budgetmate.vistas.categorias

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.budgetmate.componentes.BarraInferior
import com.example.budgetmate.util.Pantallas

@Composable
fun Categorias(
    navHostController: NavHostController,
    viewModel: CategoriasViewModel = hiltViewModel()
) {
    val listaCategorias by viewModel.categorias

    Scaffold(
        topBar = {
            Text(
                text = "Categorias",
                fontWeight = FontWeight.ExtraBold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(20.dp)
            )
        },
        floatingActionButton = {
            Box(
                modifier = Modifier.padding(16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                FloatingActionButton(
                    onClick = {
                        navHostController.navigate(Pantallas.AgregarEditarCategoria.ruta + "/-1" + "/agregar")
                    },
                    containerColor = MaterialTheme.colorScheme.surfaceTint
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = null,
                    )
                }
            }
        },
        bottomBar = {
            BarraInferior(navController = navHostController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
        ) {
            Spacer(modifier = Modifier.padding(30.dp))
            if (listaCategorias.listado.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No hay categorias registradas")
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(8.dp, 0.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(listaCategorias.listado) { categoria ->
                        Log.d("Categorias", categoria.icono)
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .clickable {
                                    navHostController.navigate(Pantallas.AgregarEditarCategoria.ruta + "/" + categoria.id + "/EditarCategoria")
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = Color(categoria.color.toColorInt()),
                            )
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .padding(15.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        viewModel.obtenerIcono(categoria.icono)?.let {
                                            Icon(
                                                imageVector = it,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                        Text(
                                            text = categoria.nombre,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                    IconButton(onClick = {
                                        viewModel.eliminarCategoria(categoria.id)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Delete,
                                            contentDescription = null,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}