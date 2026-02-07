package com.sample.bssdemo.data.mapper

import android.graphics.RectF
import android.net.Uri
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.sample.bssdemo.Utils.FaceDetections
import com.sample.bssdemo.Utils.FaceTag
import com.sample.bssdemo.data.entity.FaceDetectionsEntity

fun FaceDetections.toEntity(gson: Gson) =
    FaceDetectionsEntity(
        imageUri = uri.toString(),
        faceCount = faceCount,
        facesJson = gson.toJson(faces),
        boxesJson = gson.toJson(boxes),
        imageWidth = imageWidth,
        imageHeight = imageHeight
    )

