package com.sample.bssdemo.domain.usecases

import com.google.gson.Gson
import com.sample.bssdemo.Utils.FaceDetections
import com.sample.bssdemo.data.dao.FaceDetectionsDao
import com.sample.bssdemo.data.entity.FaceDetectionsEntity
import com.sample.bssdemo.domain.dtos.toDomain
import com.sample.bssdemo.domain.repositories.FaceDetectionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

    class GetAllFaceDetectionsUseCase @Inject constructor(
        private val repository: FaceDetectionsRepository,
        private val gson: Gson
    ) {

        operator fun invoke(): Flow<List<FaceDetections>> {
            return repository.getAll()
                .map { list -> list.map { it.toDomain(gson) } }
        }
    }



