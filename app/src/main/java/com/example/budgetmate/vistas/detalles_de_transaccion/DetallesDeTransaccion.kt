package com.example.budgetmate.vistas.detalles_de_transaccion

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.budgetmate.huh.cuentas.CuentasViewModel
import com.example.budgetmate.util.Pantallas
import com.example.budgetmate.vistas.categorias.CategoriasViewModel

@Composable
fun DetallesDeTransaccion(
    navHostController: NavHostController,
    viewModel: DetallesDeTransaccionViewModel = hiltViewModel(),
    cuentasViewModel: CuentasViewModel = hiltViewModel(),
    categoriaViewModel: CategoriasViewModel = hiltViewModel(),
    idTransaccion: Int
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .padding(8.dp),
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { navHostController.navigateUp() },
            ) {
                Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
            }
            Row {
                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "Transacciones", fontSize = 20.sp)
            }
            Row {
                IconButton(onClick = {
                    viewModel.onEvent(
                        EventoDetallesDeTransaccion.Eliminar(
                            navHostController,
                            id = idTransaccion
                        )
                    )
                }) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                }
                IconButton(onClick = {
                    viewModel.onEvent(
                        EventoDetallesDeTransaccion.Compartir(
                            context
                        )
                    )
                }) {
                    Icon(imageVector = Icons.Outlined.Share, contentDescription = null)
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (viewModel.transaccionActual.value.transaccion?.tipoTransaccion == "Ingreso") {
                                Color(0xFF43A047)
                            } else {
                                Color(0xFFE53935)
                            }
                        )
                        .padding(24.dp, 32.dp),
                    verticalArrangement = Arrangement.Center,
                ) {

                    Spacer(modifier = Modifier.height(24.dp))

                    Column {
                        Text(
                            text = "Titulo",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold
                        )
                        viewModel.transaccionActual.value.transaccion?.let {
                            Text(
                                text = it.titulo,
                                color = Color.White,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column {
                        Text(
                            text = "Cantidad",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "Q.${viewModel.transaccionActual.value.transaccion?.cantidad}",
                            color = Color.White,
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column {
                        Text(
                            text = "Tipo de transaccion",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold
                        )
                        viewModel.transaccionActual.value.transaccion?.let {
                            Text(
                                text = it.tipoTransaccion,
                                color = Color.White,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column {
                        Text(
                            text = "Categoria",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold
                        )
                        viewModel.transaccionActual.value.transaccion?.let {
                            viewModel.obtenerCategoriaPorId(it.categoria)
                            viewModel.categoria.value?.let { categoria ->
                                Row (
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    categoriaViewModel.obtenerIcono(categoria.icono)?.let { it1 ->
                                        Icon(
                                            imageVector = it1,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                    Text(
                                        text = categoria.nombre,
                                        color = Color.White,
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column {
                        Text(
                            text = "Cuenta",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold
                        )
                        viewModel.transaccionActual.value.transaccion?.let {
                            viewModel.obtenerCuentaPorId(it.cuenta)
                            viewModel.cuenta.value?.let { cuenta ->
                                Row (
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    cuentasViewModel.obtenerIcono(cuenta.icono)?.let { it1 ->
                                        Icon(
                                            imageVector = it1,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                    Text(
                                        text = cuenta.nombre,
                                        color = Color.White,
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column {
                        Text(
                            text = "Fecha",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold
                        )
                        viewModel.transaccionActual.value.transaccion?.let {
                            Text(
                                text = it.fecha,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column {
                        Text(
                            text = "Nota",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold
                        )
                        viewModel.transaccionActual.value.transaccion?.let {
                            Text(
                                text = it.nota,
                                color = Color.White,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = {
                        navHostController.navigate(Pantallas.AgregarEditarTransaccion.ruta + "/${idTransaccion}" + "/${Pantallas.DetallesDeTransaccion.ruta}")
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}