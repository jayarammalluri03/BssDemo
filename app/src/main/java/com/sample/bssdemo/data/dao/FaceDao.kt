package com.sample.bssdemo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sample.bssdemo.data.entity.FaceDetectionsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FaceDetectionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FaceDetectionsEntity)

    @Query("SELECT * FROM face_detections WHERE imageUri = :uri")
    suspend fun getByUri(uri: String): FaceDetectionsEntity?

    @Query("SELECT * FROM face_detections")
    fun getAll(): Flow<List<FaceDetectionsEntity>>


    @Update
    suspend fun update(entity: FaceDetectionsEntity)
}
