package com.sample.bssdemo.domain.usecases

data class FaceDetectionUseCases(
    val insert: InsertFaceDetectionUseCase,
    val update: UpdateFaceDetectionUseCase,
    val get: GetFaceDetectionUseCase,
    val getAll: GetAllFaceDetectionsUseCase
)