package com.infnetseg.tp3segcrypto.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.infnetseg.tp3segcrypto.converters.CriptoConverter
import com.infnetseg.tp3segcrypto.dao.AvaliacaoDAO
import com.infnetseg.tp3segcrypto.entities.Avaliacao

@Database(entities = [Avaliacao::class], version = 1)
@TypeConverters(CriptoConverter::class)
abstract class BancoDados: RoomDatabase() {
    abstract fun avaliacaoDao(): AvaliacaoDAO
}