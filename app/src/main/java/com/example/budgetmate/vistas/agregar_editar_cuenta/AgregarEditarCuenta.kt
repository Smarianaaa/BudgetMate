package com.example.budgetmate.vistas.agregar_editar_cuenta

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
import com.example.budgetmate.util.Pantallas
import com.example.budgetmate.vistas.agregar_editar_transaccion.AgregarEditarTransaccionViewModel
import com.example.budgetmate.vistas.detalles_de_transaccion.EventoDetallesDeTransaccion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarEditarCuenta(
    navHostController: NavHostController,
    cuentaViewModel: AgregarEditarCuentaViewModel = hiltViewModel(),
    idCuenta: Int,
    pantallaAnterior: String
) {
    BackHandler {
        cuentaViewModel.onEvent(AgregarEditarCuentaEvento.AbrirDialogo)
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
                cuentaViewModel.onEvent(AgregarEditarCuentaEvento.AbrirDialogo)
            }) {
                Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
            }
            Text(
                text = (if (pantallaAnterior == "EditarCuenta") "Editar Cuenta" else "Agregar Cuenta"),
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(36.dp))
        }

        if (cuentaViewModel.estadoDeDialogo.value.estado) {
            AlertDialog(
                onDismissRequest = {
                    cuentaViewModel.onEvent(AgregarEditarCuentaEvento.CerrarDialogo)
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
                                text = cuentaViewModel.estadoDeDialogo.value.texto,
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
                                        cuentaViewModel.onEvent(AgregarEditarCuentaEvento.CerrarDialogo)
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
                    value = cuentaViewModel.nombre.value.texto,
                    onValueChange = { cuentaViewModel.onEvent(AgregarEditarCuentaEvento.IngresarNombre(it)) },
                    modifier = Modifier.width(280.dp),
                    placeholder = {
                        Text(
                            text = cuentaViewModel.nombre.value.hint,
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
                    expanded = cuentaViewModel.tipo.value.estaExpandido,
                    onExpandedChange = {
                        cuentaViewModel.onEvent(AgregarEditarCuentaEvento.TipoExpandido)
                    },
                    modifier = Modifier.width(280.dp)
                ) {
                    TextField(
                        placeholder = {
                            Text(
                                text = cuentaViewModel.tipo.value.hint,
                                modifier = Modifier.alpha(0.5f)
                            )
                        },
                        readOnly = true,
                        value = cuentaViewModel.tipo.value.opcionSeleccionada,
                        onValueChange = {},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = cuentaViewModel.tipo.value.estaExpandido)
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
                        expanded = cuentaViewModel.tipo.value.estaExpandido,
                        onDismissRequest = {
                            cuentaViewModel.onEvent(AgregarEditarCuentaEvento.DescartarTipo)
                        }
                    ) {
                        cuentaViewModel.tiposCuenta.forEach { tipo ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = tipo,
                                        modifier = Modifier.alpha(0.5f)
                                    )
                                },
                                onClick = {
                                    cuentaViewModel.onEvent(AgregarEditarCuentaEvento.IngresarTipo(tipo))
                                    cuentaViewModel.onEvent(AgregarEditarCuentaEvento.DescartarTipo)
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                ExposedDropdownMenuBox(
                    expanded = cuentaViewModel.icono.value.estaExpandido,
                    onExpandedChange = {
                        cuentaViewModel.onEvent(AgregarEditarCuentaEvento.IconoExpandido)
                    },
                    modifier = Modifier.width(280.dp)
                ) {
                    TextField(
                        placeholder = {
                            Text(
                                text = cuentaViewModel.icono.value.hint,
                                modifier = Modifier.alpha(0.5f)
                            )
                        },
                        readOnly = true,
                        value = cuentaViewModel.icono.value.opcionSeleccionada.replace("Outlined.", ""),
                        leadingIcon = {
                            if (cuentaViewModel.icono.value.opcionSeleccionada != "") {
                                Icon(
                                    imageVector = cuentaViewModel.obtenerIcono(cuentaViewModel.icono.value.opcionSeleccionada),
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
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = cuentaViewModel.icono.value.estaExpandido)
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
                        expanded = cuentaViewModel.icono.value.estaExpandido,
                        onDismissRequest = {
                            cuentaViewModel.onEvent(AgregarEditarCuentaEvento.DescartarIcono)
                        }
                    ) {
                        cuentaViewModel.listaIconos.forEach { icono ->
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
                                    cuentaViewModel.onEvent(AgregarEditarCuentaEvento.IngresarIcono(icono.name))
                                    cuentaViewModel.onEvent(AgregarEditarCuentaEvento.DescartarIcono)
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                ExposedDropdownMenuBox(
                    expanded = cuentaViewModel.color.value.estaExpandido,
                    onExpandedChange = {
                        cuentaViewModel.onEvent(AgregarEditarCuentaEvento.ColorExpandido)
                    },
                    modifier = Modifier.width(280.dp)
                ) {
                    TextField(
                        placeholder = {
                            Text(
                                text = cuentaViewModel.color.value.hint,
                                modifier = Modifier.alpha(0.5f)
                            )
                        },
                        readOnly = true,
                        value = cuentaViewModel.color.value.opcionSeleccionada,
                        onValueChange = {},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = cuentaViewModel.color.value.estaExpandido)
                        },
                        leadingIcon = {
                            if (cuentaViewModel.color.value.opcionSeleccionada != "") {
                                Icon(
                                    imageVector = Icons.Outlined.Circle,
                                    contentDescription = null,
                                    tint = Color(cuentaViewModel.color.value.opcionSeleccionada.toColorInt()),
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
                        expanded = cuentaViewModel.color.value.estaExpandido,
                        onDismissRequest = {
                            cuentaViewModel.onEvent(AgregarEditarCuentaEvento.DescartarColor)
                        }
                    ) {
                        cuentaViewModel.listasColores.forEach { color ->
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
                                    cuentaViewModel.onEvent(
                                        AgregarEditarCuentaEvento.IngresarColor(
                                            cuentaViewModel.convertirColor(color)
                                        )
                                    )
                                    cuentaViewModel.onEvent(AgregarEditarCuentaEvento.DescartarColor)
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = cuentaViewModel.saldo.value.texto,
                    onValueChange = { cuentaViewModel.onEvent(AgregarEditarCuentaEvento.IngresarSaldo(it)) },
                    modifier = Modifier.width(280.dp),
                    placeholder = {
                        Text(
                            text = cuentaViewModel.saldo.value.hint,
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
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        cuentaViewModel.onEvent(
                            AgregarEditarCuentaEvento.GuardarEditarCuenta(
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