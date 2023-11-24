package com.example.budgetmate.vistas.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.budgetmate.componentes.TarjetaDeResumen
import com.example.budgetmate.componentes.TarjetaDeTransaccion
import com.example.budgetmate.util.Pantallas

@Composable
fun Dashboard(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val estadoDeTarjeta by viewModel.estadoDeTarjeta
    val listaReciente by viewModel.transaccionesRecientes

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = {
                navController.navigate("acerca_de")
            }) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseOnSurface, MaterialTheme.shapes.large)
                .padding(10.dp, 0.dp)
        ) {
            Column {
                TarjetaDeResumen(estadoDeTarjeta)
                Button(
                    onClick = {
                        navController.navigate(Pantallas.Cuentas.ruta)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 8.dp),
                ) {
                    Text(text = "Ver todas las cuentas")
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.large)
                .padding(8.dp, 16.dp)
        ) {
            Text(text = "Transacciones recientes", style = MaterialTheme.typography.titleSmall)
        }

        if (listaReciente.listado.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No hay transacciones recientes")
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(8.dp, 0.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listaReciente.listado) {
                    TarjetaDeTransaccion(transaccion = it) {
                        navController.navigate(Pantallas.DetallesDeTransaccion.conArgumentos(it.id.toString()))
                    }
                }
            }
        }
    }
}
