package com.sample.bssdemo.di

import com.sample.bssdemo.data.repository.FaceDetectorRepoImpl
import com.sample.bssdemo.domain.repositories.FaceDetectionsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindFaceRepository(
        impl: FaceDetectorRepoImpl
    ): FaceDetectionsRepository
}