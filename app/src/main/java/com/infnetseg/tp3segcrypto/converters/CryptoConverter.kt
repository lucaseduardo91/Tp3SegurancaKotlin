package com.infnetseg.tp3segcrypto.converters

import androidx.room.TypeConverter
import com.infnetseg.tp3segcrypto.security.CriptoString

class CriptoConverter {
    @TypeConverter
    fun fromCriptoString(value: CriptoString?): String? {
        return value?.getCriptoBase64()
    }

    @TypeConverter
    fun toCriptoString(value: String?): CriptoString? {
        val cripto = CriptoString()
        cripto.setCriptoBase64(value!!)
        return cripto
    }
}