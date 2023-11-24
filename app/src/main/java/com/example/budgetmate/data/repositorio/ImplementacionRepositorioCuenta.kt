package com.example.budgetmate.data.repositorio

import com.example.budgetmate.data.data_source.CuentaDAO
import com.example.budgetmate.dominio.modelos.Cuenta
import com.example.budgetmate.dominio.repositorios.RepositorioCuenta
import kotlinx.coroutines.flow.Flow

class ImplementacionRepositorioCuenta (
    private val dao: CuentaDAO
) : RepositorioCuenta {
    override suspend fun insertar(cuenta: Cuenta) {
        dao.insertarCuenta(cuenta)
    }

    override suspend fun actualizar(cuenta: Cuenta) {
        dao.actualizarCuenta(cuenta)
    }

    override suspend fun obtenerTodasCuentas(): Flow<List<Cuenta>> {
        return dao.obtenerTodasCuentas()
    }

    override suspend fun obtenerCuentaPorId(id: Int): Flow<Cuenta> {
        return dao.obtenerCuentaPorId(id)
    }

    override suspend fun eliminarCuentaPorId(id: Int) {
        dao.eliminarCuentaPorId(id)
    }

}