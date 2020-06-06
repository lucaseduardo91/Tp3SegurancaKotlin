package com.infnetseg.tp3segcrypto.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.infnetseg.tp3segcrypto.entities.Avaliacao

@Dao
interface AvaliacaoDAO {

    @Query("SELECT * FROM avaliacao")
    fun getAll(): List<Avaliacao>

    @Query("SELECT * FROM avaliacao WHERE id = :avaliacaoId")
    fun getById(avaliacaoId: Int): Avaliacao

    @Query("SELECT * FROM avaliacao WHERE id IN (:avaliacaoIds)")
    fun loadAllByIds(avaliacaoIds: IntArray): List<Avaliacao>

    @Query("SELECT * FROM avaliacao WHERE serie = :serieGerado")
    fun loadBySerie(serieGerado: String): List<Avaliacao>?

    @Query("SELECT * FROM avaliacao ORDER BY bairro")
    fun getAllData(): List<Avaliacao>

    @Insert
    fun insertAll(vararg avaliacao: Avaliacao)

    @Delete
    fun delete(avaliacao: Avaliacao)
}