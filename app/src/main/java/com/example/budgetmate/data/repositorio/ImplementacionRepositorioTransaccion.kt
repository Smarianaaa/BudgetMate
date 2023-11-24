package com.example.budgetmate.data.repositorio

import com.example.budgetmate.data.data_source.TransaccionDAO
import com.example.budgetmate.dominio.modelos.Transaccion
import com.example.budgetmate.dominio.repositorios.RepositorioTransaccion
import kotlinx.coroutines.flow.Flow

class ImplementacionRepositorioTransaccion (
    private val dao: TransaccionDAO
) : RepositorioTransaccion {
    override suspend fun insertar(transaccion: Transaccion) {
        dao.insertarTransaccion(transaccion)
    }

    override suspend fun actualizar(transaccion: Transaccion) {
        dao.actualizarTransaccion(transaccion)
    }

    override suspend fun obtenerTodasTransacciones(): Flow<List<Transaccion>> {
        return dao.obtenerTodasTransacciones()
    }

    override suspend fun obtenerTransaccionPorId(id: Int): Flow<Transaccion> {
        return dao.obtenerTransaccionPorId(id)
    }

    override suspend fun eliminarTransaccionPorId(id: Int) {
        dao.eliminarTransaccionPorId(id)
    }

    override suspend fun obtenerTransaccionesPorNombreCategoria(nombre: String): Flow<List<Transaccion>> {
        return dao.obtenerTransaccionesPorNombreCategoria(nombre)
    }

    override suspend fun obtenerTransaccionesPorCuenta(id: Int): Flow<List<Transaccion>> {
        return dao.obtenerTransaccionesPorCuenta(id)
    }
}