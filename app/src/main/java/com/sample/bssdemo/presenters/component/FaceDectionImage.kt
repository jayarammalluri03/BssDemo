package com.sample.bssdemo.presenters.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sample.bssdemo.Utils.FaceDetections

@Composable
fun FaceImageItem(item: FaceDetections, onclick: () -> Unit, onSelectedFaceIndex: (Int) -> Unit) {
    val ratio =
        if (item.imageWidth > 0 && item.imageHeight > 0)
            item.imageWidth.toFloat() / item.imageHeight.toFloat()
        else
            1f
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratio)
            .padding(8.dp)
    ) {

        AsyncImage(
            model = item.uri,
            contentDescription = null,
            modifier = Modifier.matchParentSize().clickable{
                onclick()
            },
            contentScale = ContentScale.FillBounds
        )

        Canvas(modifier = Modifier.matchParentSize()) {

            val scaleX = size.width / item.imageWidth
            val scaleY = size.height / item.imageHeight

            item.boxes.forEach { rect ->

                drawRect(
                    color = Color.Red,
                    topLeft = Offset(
                        rect.left * scaleX,
                        rect.top * scaleY
                    ),
                    size = Size(
                        rect.width() * scaleX,
                        rect.height() * scaleY
                    ),
                    style = Stroke(width = 4f)
                )
            }
        }
    }
}