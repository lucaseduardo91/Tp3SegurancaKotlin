package com.infnetseg.tp3segcrypto.repository

import android.content.Context
import androidx.room.Room

class DatabaseFactory {
    companion object{
        private var bancoDados: BancoDados? = null

        fun getInstance(context: Context?) : BancoDados{
            if(bancoDados == null)
                bancoDados = Room.databaseBuilder(
                    context!!,
                    BancoDados::class.java, "avaliacoes"
                ).fallbackToDestructiveMigration().build()

            return bancoDados as BancoDados
        }

    }
}