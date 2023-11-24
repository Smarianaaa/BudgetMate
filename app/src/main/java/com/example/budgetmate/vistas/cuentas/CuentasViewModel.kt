package com.example.budgetmate.huh.cuentas

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.BusinessCenter
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgetmate.dominio.modelos.Cuenta
import com.example.budgetmate.dominio.repositorios.RepositorioCuenta
import com.example.budgetmate.dominio.repositorios.RepositorioTransaccion
import com.example.budgetmate.huh.agregar_editar_cuenta.ListadoDeCuentas
import com.example.budgetmate.vistas.dashboard.EstadoDeTarjeta
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CuentasViewModel @Inject constructor(
    private val repositorioCuenta: RepositorioCuenta,
    private val repositorioTransaccion: RepositorioTransaccion
) : ViewModel() {
    val listaIconos = listOf(
        Icons.Outlined.AccountBalance,
        Icons.Outlined.Money,
        Icons.Outlined.Wallet,
        Icons.Outlined.CreditCard,
        Icons.Outlined.AccountBalanceWallet,
        Icons.Outlined.Business,
        Icons.Outlined.BusinessCenter
    )

    fun obtenerIcono(icono: String): ImageVector? {
        return listaIconos.find { it.name == icono }
    }

    private val _cuentas = mutableStateOf(ListadoDeCuentas())
    val cuentas: State<ListadoDeCuentas> = _cuentas

    init {
        viewModelScope.launch {
            repositorioCuenta.obtenerTodasCuentas().collect { cuentas ->
                _cuentas.value = ListadoDeCuentas(
                    listado = cuentas
                )
            }
        }
    }

    fun obtenerBalanceDeCuenta(cuenta: Cuenta): Flow<EstadoDeTarjeta> = flow {
        var balance : EstadoDeTarjeta
        repositorioTransaccion.obtenerTransaccionesPorCuenta(cuenta.id)
            .collect { transacciones ->
                balance = EstadoDeTarjeta(
                    balanceTotal = (transacciones.filter { it.tipoTransaccion == "Ingreso" }
                        .sumOf { it.cantidad } + cuenta.saldo )
                            - transacciones.filter { it.tipoTransaccion == "Gasto" }
                        .sumOf { it.cantidad },
                    ingreso = (transacciones.filter { it.tipoTransaccion == "Ingreso" }
                        .sumOf { it.cantidad } + cuenta.saldo ),
                    gasto = transacciones.filter { it.tipoTransaccion == "Gasto" }
                        .sumOf { it.cantidad }
                )
                emit(balance)
            }
    }

    fun eliminarCuenta(cuenta: Cuenta) {
        viewModelScope.launch {
            repositorioCuenta.eliminarCuentaPorId(cuenta.id)
        }
    }
}