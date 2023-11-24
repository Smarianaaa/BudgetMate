package com.example.budgetmate.data.repositorio

import com.example.budgetmate.data.data_source.CategoriaDAO
import com.example.budgetmate.dominio.modelos.Categoria
import com.example.budgetmate.dominio.repositorios.RepositorioCategoria
import kotlinx.coroutines.flow.Flow

class ImplementacionRepositorioCategoria (
    private val dao: CategoriaDAO
) : RepositorioCategoria {
    override suspend fun insertar(categoria: Categoria) {
        dao.insertarCategoria(categoria)
    }

    override suspend fun actualizar(categoria: Categoria) {
        dao.actualizarCategoria(categoria)
    }

    override suspend fun obtenerTodasCategorias(): Flow<List<Categoria>> {
        return dao.obtenerTodasCategorias()
    }

    override suspend fun obtenerCategoriaPorId(id: Int): Flow<Categoria> {
        return dao.obtenerCategoriaPorId(id)
    }

    override suspend fun eliminarCategoriaPorId(id: Int) {
        dao.eliminarCategoriaPorId(id)
    }
}
