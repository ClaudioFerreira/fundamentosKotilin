package com.example.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.orgs.model.Produto

@Dao
interface ProdutoDAO {

    @Query("SELECT * FROM Produto")
    fun buscaTodos(): List<Produto>

    @Query("SELECT * FROM Produto WHERE id = :id")
    fun buscaPorId(id: Long): Produto

    @Insert(onConflict = OnConflictStrategy.REPLACE) //(onConflict = OnConflictStrategy.REPLACE) permite que ao encontrar um conflito de id faca o update dos dados
    fun salva(vararg produto: Produto) // vararg permite passar um ou mais produtos

    @Delete()
    fun delete(produto: Produto)
}