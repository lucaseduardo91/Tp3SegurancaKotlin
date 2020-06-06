package com.infnetseg.tp3segcrypto.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.infnetseg.tp3segcrypto.security.CriptoString

@Entity
data class Avaliacao (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "serie") val serie: String,
    @ColumnInfo(name = "empresa") val empresa: CriptoString?,
    @ColumnInfo(name = "bairro") val bairro: String?,
    @ColumnInfo(name = "respostas") val respostas: String?
)