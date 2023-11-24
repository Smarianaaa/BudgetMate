package com.example.budgetmate.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.budgetmate.dominio.modelos.Categoria
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCategoria(categoria: Categoria)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun actualizarCategoria(categoria: Categoria)

    @Query("SELECT * FROM categorias")
    fun obtenerTodasCategorias(): Flow<List<Categoria>>

    @Query("SELECT * FROM categorias WHERE id = :id")
    fun obtenerCategoriaPorId(id: Int): Flow<Categoria>

    @Query("DELETE FROM categorias WHERE id = :id")
    suspend fun eliminarCategoriaPorId(id: Int)
}