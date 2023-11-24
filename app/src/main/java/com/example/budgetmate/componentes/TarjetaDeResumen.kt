package com.example.budgetmate.componentes

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.TrendingDown
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetmate.R
import com.example.budgetmate.vistas.dashboard.EstadoDeTarjeta

@Composable
fun TarjetaDeResumen(
    estadoDeTarjeta: EstadoDeTarjeta
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = stringResource(R.string.balance_total),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 16.sp,
                    )
                Text(
                    text = if (estadoDeTarjeta.balanceTotal!! >= 0) {
                        "Q.${estadoDeTarjeta.balanceTotal}"
                    } else {
                        "- Q.${-estadoDeTarjeta.balanceTotal}"
                    },
                    fontSize = 40.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TarjetitaResumen(
                        color = Color(0xFF00CB51),
                        imageVector = Icons.Outlined.TrendingUp,
                        heading = "Ingresos",
                        content = "+ Q.${estadoDeTarjeta.ingreso}"
                    )
                    TarjetitaResumen(
                        color = Color(0xFFCB0000),
                        imageVector = Icons.Outlined.TrendingDown,
                        heading = "Gastos",
                        content = "- Q.${estadoDeTarjeta.gasto}"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))


        }

    }
}