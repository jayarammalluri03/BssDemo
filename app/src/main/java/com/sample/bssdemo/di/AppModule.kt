package com.sample.bssdemo.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.facedetector.FaceDetector
import com.sample.bssdemo.data.dao.FaceDetectionsDao
import com.sample.bssdemo.data.db.FaceDetectordb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): FaceDetectordb {
        return Room.databaseBuilder(
            context,
            FaceDetectordb::class.java,
            "face_db"
        ).build()
    }


    @Provides
    fun provideFaceDao(db: FaceDetectordb): FaceDetectionsDao {
        return db.faceDao()
    }



    @Provides
    @Singleton
    fun ProvideFaceDetector(@ApplicationContext context: Context): FaceDetector {
        val baseOptions = BaseOptions.builder()
            .setModelAssetPath("models/blaze_face_short_range.tflite")
            .build()

        val options = FaceDetector.FaceDetectorOptions.builder()
            .setBaseOptions(baseOptions)
            .setMinDetectionConfidence(0.7f)   // lower confidence
            .build()

        return FaceDetector.createFromOptions(context, options)
    }
}