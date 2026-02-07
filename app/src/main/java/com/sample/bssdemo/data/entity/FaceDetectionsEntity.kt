package com.sample.bssdemo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "face_detections")
data class FaceDetectionsEntity(

    @PrimaryKey
    val imageUri: String,
    val faceCount: Int,
    val facesJson: String?,
    val boxesJson: String,

    val imageWidth: Int,
    val imageHeight: Int
)
