package com.azrinurvani.newsappwithstartedpage.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.azrinurvani.newsappwithstartedpage.domain.model.Source

@ProvidedTypeConverter
class NewsTypeConverter {

    @TypeConverter
    fun sourceToString(source: Source) : String{
        return "${source.id},${source.name}"
    }

    @TypeConverter
    fun stringToResource(source: String) : Source{
        return source.split(",").let { sourceArray->
            Source(
                id = sourceArray[0],
                name = sourceArray[1]
            )
        }
    }
}