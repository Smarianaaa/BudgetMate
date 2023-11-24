package com.example.budgetmate.vistas.dashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetmate.dominio.repositorios.RepositorioCuenta
import com.example.budgetmate.dominio.repositorios.RepositorioTransaccion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repositorioTransaccion: RepositorioTransaccion,
    private val repositorioCuenta: RepositorioCuenta
) : ViewModel() {
    private val _estadoDeTarjeta = mutableStateOf(EstadoDeTarjeta())
    val estadoDeTarjeta: State<EstadoDeTarjeta> = _estadoDeTarjeta

    private val saldoDeCuentas = mutableStateOf(0L)

    private val _transaccionesRecientes = mutableStateOf(ListadoDeTransaccionesRecientes())
    val transaccionesRecientes: State<ListadoDeTransaccionesRecientes> = _transaccionesRecientes

    init {
        viewModelScope.launch {
            repositorioCuenta.obtenerTodasCuentas().collectLatest { cuentas ->
                saldoDeCuentas.value = cuentas.sumOf { it.saldo }
                repositorioTransaccion.obtenerTodasTransacciones().collectLatest { transacciones ->
                    _estadoDeTarjeta.value = estadoDeTarjeta.value.copy(
                        balanceTotal = transacciones.filter { transaccion -> transaccion.tipoTransaccion == "Ingreso" }
                            .sumOf { it.cantidad } + saldoDeCuentas.value - transacciones.filter { transaccion -> transaccion.tipoTransaccion == "Gasto" }
                            .sumOf { it.cantidad },
                        ingreso = transacciones.filter { it.tipoTransaccion == "Ingreso" }
                            .sumOf { it.cantidad } + saldoDeCuentas.value,
                        gasto = transacciones.filter { it.tipoTransaccion == "Gasto" }
                            .sumOf { it.cantidad }
                    )

                    _transaccionesRecientes.value = transaccionesRecientes.value.copy(
                        listado = transacciones.takeLast(4).reversed()
                    )
                }
            }
        }
    }
}