package com.sample.bssdemo.data.entity.typeConverters

import android.graphics.RectF
import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.sample.bssdemo.Utils.FaceTag

class FaceConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromRectList(list: List<RectF>): String = gson.toJson(list)

    @TypeConverter
    fun toRectList(json: String): List<RectF> =
        gson.fromJson(json, object : TypeToken<List<RectF>>() {}.type)

    @TypeConverter
    fun fromFaceList(list: List<FaceTag>?): String? =
        gson.toJson(list)

    @TypeConverter
    fun toFaceList(json: String?): List<FaceTag>? =
        gson.fromJson(json, object : TypeToken<List<FaceTag>>() {}.type)


}