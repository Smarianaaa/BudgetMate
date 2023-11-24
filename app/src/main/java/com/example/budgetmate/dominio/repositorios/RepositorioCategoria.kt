package com.example.budgetmate.dominio.repositorios

import com.example.budgetmate.dominio.modelos.Categoria
import kotlinx.coroutines.flow.Flow

interface RepositorioCategoria {
    suspend fun insertar(categoria: Categoria)
    suspend fun actualizar(categoria: Categoria)
    suspend fun obtenerTodasCategorias(): Flow<List<Categoria>>
    suspend fun obtenerCategoriaPorId(id:Int): Flow<Categoria>
    suspend fun eliminarCategoriaPorId(id:Int)
}