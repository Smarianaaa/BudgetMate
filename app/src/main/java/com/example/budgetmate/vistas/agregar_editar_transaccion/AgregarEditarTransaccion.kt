package com.example.budgetmate.vistas.agregar_editar_transaccion

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.budgetmate.huh.cuentas.CuentasViewModel
import com.example.budgetmate.vistas.categorias.CategoriasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarEditarTransaccion(
    navHostController: NavHostController,
    viewModel: AgregarEditarTransaccionViewModel = hiltViewModel(),
    cuentasViewModel: CuentasViewModel = hiltViewModel(),
    categoriaViewModel: CategoriasViewModel = hiltViewModel(),
    idTransaccion: Int,
    pantallaAnterior: String
) {
    val listaCuentas by cuentasViewModel.cuentas
    val listaCategorias by categoriaViewModel.categorias

    val tiposDeTransaccion = listOf(
        "Gasto",
        "Ingreso"
    )

    BackHandler {
        viewModel.onEvent(AgregarEditarTransaccionEvento.AbrirDialogo)
    }
    val context = LocalContext.current

    Column(
        modifier = Modifier
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
                onClick = {
                    viewModel.onEvent(AgregarEditarTransaccionEvento.AbrirDialogo)
                },
            ) {
                Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
            }

            Text(text = "Transacciones", fontSize = 20.sp)
            Spacer(modifier = Modifier.width(36.dp))
        }

        if (viewModel.estadoDeDialogo.value.estado) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.onEvent(AgregarEditarTransaccionEvento.CerrarDialogo)
                },
                content = {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp,
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = viewModel.estadoDeDialogo.value.texto,
                                fontSize = 20.sp
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(
                                    onClick = {
                                        navHostController.navigateUp()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF243D25)
                                    )
                                ) {
                                    Text(
                                        text = "Si",
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(24.dp, 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = viewModel.titulo.value.texto,
                    onValueChange = {
                        viewModel.onEvent(
                            AgregarEditarTransaccionEvento.IngresarTitulo(
                                it
                            )
                        )
                    },
                    modifier = Modifier.width(280.dp),
                    placeholder = {
                        Text(
                            text = viewModel.titulo.value.hint,
                            modifier = Modifier.alpha(0.5f)
                        )
                    },
                    shape = RoundedCornerShape(9.dp),
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = viewModel.cantidad.value.texto,
                    onValueChange = {
                        viewModel.onEvent(
                            AgregarEditarTransaccionEvento.IngresarCantidad(
                                it
                            )
                        )
                    },
                    modifier = Modifier.width(280.dp),
                    placeholder = {
                        Text(
                            text = viewModel.cantidad.value.hint,
                            modifier = Modifier.alpha(0.5f)
                        )
                    },
                    shape = RoundedCornerShape(9.dp),
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Decimal
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                ExposedDropdownMenuBox(
                    expanded = viewModel.tipoDeTransaccion.value.estaExpandido,
                    onExpandedChange = {
                        viewModel.onEvent(AgregarEditarTransaccionEvento.CambioExpandidoTipo)
                    },
                    modifier = Modifier.width(280.dp)
                ) {
                    TextField(
                        placeholder = {
                            Text(
                                text = viewModel.tipoDeTransaccion.value.hint,
                                modifier = Modifier.alpha(0.5f)
                            )
                        },
                        readOnly = true,
                        value = viewModel.tipoDeTransaccion.value.opcionSeleccionada,
                        onValueChange = { },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = viewModel.tipoDeTransaccion.value.estaExpandido
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = viewModel.tipoDeTransaccion.value.estaExpandido,
                        onDismissRequest = {
                            viewModel.onEvent(AgregarEditarTransaccionEvento.DescartarTipo)
                        }
                    ) {
                        tiposDeTransaccion.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = selectionOption)
                                },
                                onClick = {
                                    viewModel.onEvent(
                                        AgregarEditarTransaccionEvento.CambiarTipo(
                                            selectionOption
                                        )
                                    )
                                    viewModel.onEvent(AgregarEditarTransaccionEvento.DescartarTipo)
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                ExposedDropdownMenuBox(
                    expanded = viewModel.cuentas.value.estaExpandido,
                    onExpandedChange = {
                        viewModel.onEvent(AgregarEditarTransaccionEvento.CambioExpandidoCuenta)
                    },
                    modifier = Modifier.width(280.dp)
                ) {
                    TextField(
                        placeholder = {
                            Text(
                                text = viewModel.cuentas.value.hint,
                                modifier = Modifier.alpha(0.5f)
                            )
                        },
                        readOnly = true,
                        value = viewModel.cuentas.value.opcionSeleccionada,
                        onValueChange = { },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = viewModel.cuentas.value.estaExpandido
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = viewModel.cuentas.value.estaExpandido,
                        onDismissRequest = {
                            viewModel.onEvent(AgregarEditarTransaccionEvento.DescartarCuenta)
                        }
                    ) {
                        listaCuentas.listado.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = {
                                    Row {
                                        cuentasViewModel.obtenerIcono(
                                            selectionOption.icono
                                        )?.let {
                                            Icon(
                                                imageVector = it,
                                                contentDescription = null,
                                                modifier = Modifier.padding(end = 8.dp)
                                            )
                                        }
                                        Text(text = selectionOption.nombre)
                                    }
                                },
                                onClick = {
                                    viewModel.onEvent(
                                        AgregarEditarTransaccionEvento.CambiarCuenta(
                                            selectionOption
                                        )
                                    )
                                    viewModel.onEvent(AgregarEditarTransaccionEvento.DescartarCuenta)
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                ExposedDropdownMenuBox(
                    expanded = viewModel.categorias.value.estaExpandido,
                    onExpandedChange = {
                        viewModel.onEvent(AgregarEditarTransaccionEvento.CambioExpandidoCategoria)
                    },
                    modifier = Modifier.width(280.dp)
                ) {
                    TextField(
                        placeholder = {
                            Text(
                                text = viewModel.categorias.value.hint,
                                modifier = Modifier.alpha(0.5f)
                            )
                        },
                        readOnly = true,
                        value = viewModel.categorias.value.categoria.nombre,
                        onValueChange = { },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = viewModel.categorias.value.estaExpandido
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = viewModel.categorias.value.estaExpandido,
                        onDismissRequest = {
                            viewModel.onEvent(AgregarEditarTransaccionEvento.DescartarCategoria)
                        }
                    ) {
                        listaCategorias.listado.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = {
                                    Row {
                                        categoriaViewModel.obtenerIcono(
                                            selectionOption.icono
                                        )?.let {
                                            Icon(
                                                imageVector = it,
                                                contentDescription = null,
                                                modifier = Modifier.padding(end = 8.dp)
                                            )
                                        }
                                        Text(text = selectionOption.nombre)
                                    }
                                },
                                onClick = {
                                    viewModel.onEvent(
                                        AgregarEditarTransaccionEvento.CambiarCategoria(
                                            selectionOption
                                        )
                                    )
                                    viewModel.onEvent(AgregarEditarTransaccionEvento.DescartarCategoria)
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = viewModel.etiquetas.value.texto,
                    onValueChange = {
                        viewModel.onEvent(
                            AgregarEditarTransaccionEvento.IngresarEtiquetas(
                                it
                            )
                        )
                    },
                    modifier = Modifier.width(280.dp),
                    placeholder = {
                        Text(
                            text = viewModel.etiquetas.value.hint,
                            modifier = Modifier.alpha(0.5f)
                        )
                    },
                    shape = RoundedCornerShape(9.dp),
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = viewModel.nota.value.texto,
                    onValueChange = {
                        viewModel.onEvent(
                            AgregarEditarTransaccionEvento.IngresarNota(
                                it
                            )
                        )
                    },
                    modifier = Modifier.width(280.dp),
                    placeholder = {
                        Text(
                            text = viewModel.nota.value.hint,
                            modifier = Modifier.alpha(0.5f)
                        )
                    },
                    shape = RoundedCornerShape(9.dp),
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        viewModel.onEvent(
                            AgregarEditarTransaccionEvento.GuardarEditarTransaccion(
                                context,
                                navHostController
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1F8123),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "GUARDAR")
                }
            }

        }
    }
}