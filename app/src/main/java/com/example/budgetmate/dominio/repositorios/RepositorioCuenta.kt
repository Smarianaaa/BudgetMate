package com.example.budgetmate.dominio.repositorios

import com.example.budgetmate.dominio.modelos.Cuenta
import kotlinx.coroutines.flow.Flow

interface RepositorioCuenta {
    suspend fun insertar(cuenta: Cuenta)
    suspend fun actualizar(cuenta: Cuenta)
    suspend fun obtenerTodasCuentas(): Flow<List<Cuenta>>
    suspend fun obtenerCuentaPorId(id:Int): Flow<Cuenta>
    suspend fun eliminarCuentaPorId(id:Int)
}