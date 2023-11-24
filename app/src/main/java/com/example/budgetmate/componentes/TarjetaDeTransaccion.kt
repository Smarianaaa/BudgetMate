package com.example.budgetmate.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NorthEast
import androidx.compose.material.icons.outlined.SouthEast
import androidx.compose.material.icons.outlined.SouthWest
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetmate.dominio.modelos.Transaccion

@Composable
fun TarjetaDeTransaccion(transaccion: Transaccion, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .background(
                color = if (transaccion.tipoTransaccion == "Gasto") {
                    Color(0xFFE53935)
                } else {
                    Color(0xFF43A047)
                }
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = if (transaccion.tipoTransaccion == "Gasto") {
                Icons.Outlined.SouthEast
            } else {
                Icons.Outlined.NorthEast
            },
            contentDescription = transaccion.etiquetas,
            modifier = Modifier.size(40.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = transaccion.titulo,
                    color = Color.White,
                    fontSize = 16.sp,
                )

                Text(
                    modifier = Modifier.width(64.dp),
                    text = transaccion.etiquetas,
                    color = Color.White.copy(0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = if (transaccion.tipoTransaccion == "Gasto") {
                        "-Q.${transaccion.cantidad}"
                    } else {
                        "+Q.${transaccion.cantidad}"
                    },
                    color = Color.White,
                    fontSize = 16.sp,
                )

                Text(
                    text = transaccion.fecha,
                    color = Color.White.copy(0.7f),
                )
            }
        }

    }
}