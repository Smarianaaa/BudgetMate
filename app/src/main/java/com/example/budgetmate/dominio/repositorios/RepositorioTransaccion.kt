package com.example.budgetmate.dominio.repositorios

import com.example.budgetmate.dominio.modelos.Transaccion
import kotlinx.coroutines.flow.Flow

interface RepositorioTransaccion {
    suspend fun insertar(transaccion: Transaccion)
    suspend fun actualizar(transaccion: Transaccion)
    suspend fun obtenerTodasTransacciones(): Flow<List<Transaccion>>
    suspend fun obtenerTransaccionPorId(id:Int): Flow<Transaccion>
    suspend fun eliminarTransaccionPorId(id:Int)
    suspend fun obtenerTransaccionesPorNombreCategoria(nombre: String): Flow<List<Transaccion>>
    suspend fun obtenerTransaccionesPorCuenta(id: Int): Flow<List<Transaccion>>
}