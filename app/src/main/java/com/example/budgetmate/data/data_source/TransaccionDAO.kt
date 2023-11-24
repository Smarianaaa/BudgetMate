package com.example.budgetmate.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.budgetmate.dominio.modelos.Transaccion
import kotlinx.coroutines.flow.Flow

@Dao
interface TransaccionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTransaccion(transaccion: Transaccion)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun actualizarTransaccion(transaccion: Transaccion)

    @Query("SELECT * FROM transacciones")
    fun obtenerTodasTransacciones(): Flow<List<Transaccion>>

    @Query("SELECT * FROM transacciones WHERE id = :id")
    fun obtenerTransaccionPorId(id: Int): Flow<Transaccion>

    @Query("DELETE FROM transacciones WHERE id = :id")
    suspend fun eliminarTransaccionPorId(id: Int)

    @Query("SELECT * FROM transacciones WHERE categoria = :nombre")
    fun obtenerTransaccionesPorNombreCategoria(nombre: String): Flow<List<Transaccion>>

    @Query("SELECT * FROM transacciones WHERE cuenta = :id")
    fun obtenerTransaccionesPorCuenta(id: Int): Flow<List<Transaccion>>
}