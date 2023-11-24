package com.example.budgetmate.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.budgetmate.dominio.modelos.Cuenta
import kotlinx.coroutines.flow.Flow

@Dao
interface CuentaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCuenta(cuenta: Cuenta)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun actualizarCuenta(cuenta: Cuenta)

    @Query("SELECT * FROM cuentas")
    fun obtenerTodasCuentas(): Flow<List<Cuenta>>

    @Query("SELECT * FROM cuentas WHERE id = :id")
    fun obtenerCuentaPorId(id: Int): Flow<Cuenta>

    @Query("DELETE FROM cuentas WHERE id = :id")
    suspend fun eliminarCuentaPorId(id: Int)
}