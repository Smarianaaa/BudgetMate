package com.example.budgetmate.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.budgetmate.dominio.modelos.Categoria
import com.example.budgetmate.dominio.modelos.Cuenta
import com.example.budgetmate.dominio.modelos.Transaccion

@Database(
    entities = [
        Transaccion::class,
        Cuenta::class,
        Categoria::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class TransaccionesBD : RoomDatabase() {
    abstract val transaccionDao: TransaccionDAO
    abstract val cuentaDao: CuentaDAO
    abstract val categoriaDao: CategoriaDAO

    companion object {
        const val DATABASE_NAME = "transacciones_bd"
    }
}