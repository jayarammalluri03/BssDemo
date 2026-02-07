package com.sample.bssdemo.Utils

import android.graphics.RectF
import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FaceDetections(
    val uri: Uri,
    val faceCount: Int,
    val faces: List<FaceTag>?,
    val boxes: List<RectF>,
    val imageWidth: Int,
    val imageHeight: Int
) : Parcelable
