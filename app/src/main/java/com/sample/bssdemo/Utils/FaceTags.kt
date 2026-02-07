package com.sample.bssdemo.Utils

import android.graphics.RectF
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class FaceTag(
    val rect: RectF,
    var name: String? = null
): Parcelable