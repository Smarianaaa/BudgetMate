package com.example.budgetmate.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.budgetmate.data.data_source.TransaccionesBD
import com.example.budgetmate.data.repositorio.ImplementacionRepositorioCategoria
import com.example.budgetmate.data.repositorio.ImplementacionRepositorioCuenta
import com.example.budgetmate.data.repositorio.ImplementacionRepositorioTransaccion
import com.example.budgetmate.dominio.repositorios.RepositorioCategoria
import com.example.budgetmate.dominio.repositorios.RepositorioCuenta
import com.example.budgetmate.dominio.repositorios.RepositorioTransaccion
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuloApp {
    @Provides
    @Singleton
    fun providesQuestionsCacheDB(app: Application): TransaccionesBD {
        return Room.databaseBuilder(
            app,
            TransaccionesBD::class.java,
            TransaccionesBD.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesRepositorioTransaccion(
        db: TransaccionesBD,
    ): RepositorioTransaccion = ImplementacionRepositorioTransaccion(db.transaccionDao)

    @Provides
    @Singleton
    fun providesRepositorioCuenta(
        db: TransaccionesBD,
    ): RepositorioCuenta = ImplementacionRepositorioCuenta(db.cuentaDao)

    @Provides
    @Singleton
    fun providesRepositorioCategoria(
        db: TransaccionesBD,
    ): RepositorioCategoria = ImplementacionRepositorioCategoria(db.categoriaDao)
}