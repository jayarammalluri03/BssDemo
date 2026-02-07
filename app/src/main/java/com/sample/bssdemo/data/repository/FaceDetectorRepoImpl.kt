package com.sample.bssdemo.data.repository

import com.sample.bssdemo.data.dao.FaceDetectionsDao
import com.sample.bssdemo.data.entity.FaceDetectionsEntity
import com.sample.bssdemo.domain.repositories.FaceDetectionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FaceDetectorRepoImpl @Inject constructor(
    private val dao: FaceDetectionsDao
) : FaceDetectionsRepository {

    override suspend fun insert(data: FaceDetectionsEntity) {
        dao.insert(data)
    }

    override suspend fun getByUri(uri: String): FaceDetectionsEntity? {
        return dao.getByUri(uri)
    }

    override suspend fun update(entity: FaceDetectionsEntity) {
        dao.update(entity)
    }

    override fun getAll(): Flow<List<FaceDetectionsEntity>> {
        return dao.getAll()
    }
}

