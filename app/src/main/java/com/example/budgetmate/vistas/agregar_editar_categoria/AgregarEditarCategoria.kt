package com.example.budgetmate.vistas.agregar_editar_categoria

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Circle
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.budgetmate.huh.agregar_editar_cuenta.AgregarEditarCuentaEvento
import com.example.budgetmate.huh.agregar_editar_cuenta.AgregarEditarCuentaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarEditarCategoria(
    navHostController: NavHostController,
    categoriaViewModel: AgregarEditarCategoriaViewModel = hiltViewModel(),
    idCuenta: Int,
    pantallaAnterior: String
) {
    BackHandler {
        navHostController.navigate(pantallaAnterior)
    }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Spacer(modifier = Modifier.padding(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                categoriaViewModel.onEvent(AgregarEditarCategoriaEvento.AbrirDialogo)
            }) {
                Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
            }
            Text(
                text = "Agregar cuenta",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(36.dp))
        }

        if (categoriaViewModel.estadoDeDialogo.value.estado) {
            AlertDialog(
                onDismissRequest = {
                    categoriaViewModel.onEvent(AgregarEditarCategoriaEvento.CerrarDialogo)
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
                                text = categoriaViewModel.estadoDeDialogo.value.texto,
                                fontSize = 20.sp
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Button(
                                    onClick = {
                                        categoriaViewModel.onEvent(AgregarEditarCategoriaEvento.CerrarDialogo)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFBD3030)
                                    )
                                ) {
                                    Text(
                                        text = "No",
                                    )
                                }
                                OutlinedButton(
                                    onClick = {
                                        navHostController.navigateUp()
                                    },
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = Color(0xFF243D25)
                                    ),
                                ) {
                                    Text(
                                        text = "Si",
                                        color = Color(0xFF243D25)
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
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(24.dp, 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = categoriaViewModel.nombre.value.texto,
                    onValueChange = { categoriaViewModel.onEvent(AgregarEditarCategoriaEvento.IngresarNombre(it)) },
                    modifier = Modifier.width(280.dp),
                    placeholder = {
                        Text(
                            text = categoriaViewModel.nombre.value.hint,
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
                ExposedDropdownMenuBox(
                    expanded = categoriaViewModel.icono.value.estaExpandido,
                    onExpandedChange = {
                        categoriaViewModel.onEvent(AgregarEditarCategoriaEvento.IconoExpandido)
                    },
                    modifier = Modifier.width(280.dp)
                ) {
                    TextField(
                        placeholder = {
                            Text(
                                text = categoriaViewModel.icono.value.hint,
                                modifier = Modifier.alpha(0.5f)
                            )
                        },
                        readOnly = true,
                        value = categoriaViewModel.icono.value.opcionSeleccionada.replace("Outlined.", ""),
                        leadingIcon = {
                            if (categoriaViewModel.icono.value.opcionSeleccionada != "") {
                                Icon(
                                    imageVector = categoriaViewModel.obtenerIcono(categoriaViewModel.icono.value.opcionSeleccionada),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .alpha(0.5f)
                                        .align(Alignment.CenterHorizontally)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.AccountBalance,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .alpha(0.5f)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }
                        },
                        onValueChange = {},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoriaViewModel.icono.value.estaExpandido)
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = categoriaViewModel.icono.value.estaExpandido,
                        onDismissRequest = {
                            categoriaViewModel.onEvent(AgregarEditarCategoriaEvento.DescartarIcono)
                        }
                    ) {
                        categoriaViewModel.listaIconos.forEach { icono ->
                            DropdownMenuItem(
                                text = {
                                    Icon(
                                        imageVector = icono,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .alpha(0.5f)
                                            .align(Alignment.CenterHorizontally)
                                    )
                                },
                                onClick = {
                                    categoriaViewModel.onEvent(AgregarEditarCategoriaEvento.IngresarIcono(icono.name))
                                    categoriaViewModel.onEvent(AgregarEditarCategoriaEvento.DescartarIcono)
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                ExposedDropdownMenuBox(
                    expanded = categoriaViewModel.color.value.estaExpandido,
                    onExpandedChange = {
                        categoriaViewModel.onEvent(AgregarEditarCategoriaEvento.ColorExpandido)
                    },
                    modifier = Modifier.width(280.dp)
                ) {
                    TextField(
                        placeholder = {
                            Text(
                                text = categoriaViewModel.color.value.hint,
                                modifier = Modifier.alpha(0.5f)
                            )
                        },
                        readOnly = true,
                        value = categoriaViewModel.color.value.opcionSeleccionada,
                        onValueChange = {},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoriaViewModel.color.value.estaExpandido)
                        },
                        leadingIcon = {
                            if (categoriaViewModel.color.value.opcionSeleccionada != "") {
                                Icon(
                                    imageVector = Icons.Outlined.Circle,
                                    contentDescription = null,
                                    tint = Color(categoriaViewModel.color.value.opcionSeleccionada.toColorInt()),
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .alpha(0.5f)
                                        .align(Alignment.CenterHorizontally)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Outlined.Circle,
                                    contentDescription = null,
                                    tint = Color(0xFFE57373),
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .alpha(0.5f)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = categoriaViewModel.color.value.estaExpandido,
                        onDismissRequest = {
                            categoriaViewModel.onEvent(AgregarEditarCategoriaEvento.DescartarColor)
                        }
                    ) {
                        categoriaViewModel.listasColores.forEach { color ->
                            DropdownMenuItem(
                                text = {
                                    Icon(
                                        imageVector = Icons.Outlined.Circle,
                                        contentDescription = null,
                                        tint = color,
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .alpha(0.5f)
                                            .align(Alignment.CenterHorizontally)
                                    )
                                },
                                onClick = {
                                    categoriaViewModel.onEvent(
                                        AgregarEditarCategoriaEvento.IngresarColor(
                                            categoriaViewModel.convertirColor(color)
                                        )
                                    )
                                    categoriaViewModel.onEvent(AgregarEditarCategoriaEvento.DescartarColor)
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        categoriaViewModel.onEvent(
                            AgregarEditarCategoriaEvento.GuardarEditarCategoria(
                                context,
                                navHostController
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF243D25)
                    ),
                    modifier = Modifier.width(280.dp)
                ) {
                    Text(
                        text = "Guardar",
                        color = Color.White
                    )
                }
            }
        }
    }

}