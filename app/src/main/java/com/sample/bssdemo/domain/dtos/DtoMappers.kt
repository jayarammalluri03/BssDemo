package com.sample.bssdemo.domain.dtos

import android.graphics.RectF
import android.net.Uri
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.sample.bssdemo.Utils.FaceDetections
import com.sample.bssdemo.Utils.FaceTag
import com.sample.bssdemo.data.entity.FaceDetectionsEntity



fun FaceDetectionsEntity.toDomain(gson: Gson) =
    FaceDetections(
        uri = Uri.parse(imageUri),
        faceCount = faceCount,
        faces = gson.fromJson(facesJson, object : TypeToken<List<FaceTag>>() {}.type),
        boxes = gson.fromJson(boxesJson, object : TypeToken<List<RectF>>() {}.type),
        imageWidth = imageWidth,
        imageHeight = imageHeight
    )

