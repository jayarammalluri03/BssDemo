package com.sample.bssdemo.domain.usecases

import com.google.gson.Gson
import com.sample.bssdemo.Utils.FaceDetections
import com.sample.bssdemo.data.dao.FaceDetectionsDao
import com.sample.bssdemo.data.mapper.toEntity
import com.sample.bssdemo.domain.repositories.FaceDetectionsRepository
import javax.inject.Inject

class InsertFaceDetectionUseCase @Inject constructor(
    private val repository: FaceDetectionsRepository,
    private val gson: Gson
) {

    suspend operator fun invoke(data: FaceDetections) {
        repository.insert(data.toEntity(gson))
    }
}