package com.example.budgetmate.dominio.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transacciones")
data class Transaccion (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val titulo: String,
    val cuenta: Int,
    val cantidad: Long,
    val tipoTransaccion: String,
    val categoria: Int,
    val etiquetas: String,
    val fecha: String,
    val nota: String
)