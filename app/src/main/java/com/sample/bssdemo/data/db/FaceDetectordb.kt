package com.sample.bssdemo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.sample.bssdemo.data.dao.FaceDetectionsDao
import com.sample.bssdemo.data.entity.FaceDetectionsEntity
import com.sample.bssdemo.data.entity.typeConverters.FaceConverters


@Database(entities = [FaceDetectionsEntity::class], version = 1, exportSchema = false)
@TypeConverters(FaceConverters::class)
abstract class FaceDetectordb: RoomDatabase() {

    abstract fun faceDao(): FaceDetectionsDao

}