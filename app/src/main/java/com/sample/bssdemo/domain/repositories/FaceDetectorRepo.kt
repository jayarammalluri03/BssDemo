package com.sample.bssdemo.domain.repositories

import com.sample.bssdemo.Utils.FaceDetections
import com.sample.bssdemo.data.entity.FaceDetectionsEntity
import kotlinx.coroutines.flow.Flow

interface FaceDetectionsRepository {

    suspend fun insert(data: FaceDetectionsEntity)

    suspend fun getByUri(uri: String): FaceDetectionsEntity?

    fun getAll(): Flow<List<FaceDetectionsEntity>>

    suspend fun update(entity: FaceDetectionsEntity)
}