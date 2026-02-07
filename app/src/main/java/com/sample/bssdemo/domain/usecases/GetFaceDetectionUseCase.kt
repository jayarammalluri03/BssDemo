package com.sample.bssdemo.domain.usecases

import com.google.gson.Gson
import com.sample.bssdemo.Utils.FaceDetections
import com.sample.bssdemo.data.dao.FaceDetectionsDao
import com.sample.bssdemo.domain.dtos.toDomain
import com.sample.bssdemo.domain.repositories.FaceDetectionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFaceDetectionUseCase @Inject constructor(
    private val repository: FaceDetectionsRepository,
    private val gson: Gson
) {

     operator fun invoke(uri: String): Flow<FaceDetections?> = flow {
        emit( repository.getByUri(uri)?.toDomain(gson) ?: null)
    }
}