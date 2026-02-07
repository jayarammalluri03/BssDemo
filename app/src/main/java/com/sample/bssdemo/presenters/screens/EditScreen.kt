    package com.sample.bssdemo.presenters.screens

    import androidx.compose.foundation.Canvas
    import androidx.compose.foundation.gestures.detectTapGestures
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.aspectRatio
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.material3.AlertDialog
    import androidx.compose.material3.Button
    import androidx.compose.material3.CircularProgressIndicator
    import androidx.compose.material3.Text
    import androidx.compose.material3.TextField
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateMapOf
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.saveable.rememberSaveable
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.geometry.Offset
    import androidx.compose.ui.geometry.Size
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.graphics.drawscope.Stroke
    import androidx.compose.ui.graphics.nativeCanvas
    import androidx.compose.ui.input.pointer.pointerInput
    import androidx.compose.ui.layout.ContentScale
    import coil.compose.AsyncImage
    import com.sample.bssdemo.Utils.FaceDetections
    import com.sample.bssdemo.Utils.UiState
    import com.sample.bssdemo.presenters.viewmodels.EditViewModel


    @Composable
    fun EditScreen(viewModel: EditViewModel) {

        val detection by viewModel.state.collectAsState()

        var selectedFaceIndex by rememberSaveable { mutableStateOf<Int?>(null) }
        var showDialog by rememberSaveable { mutableStateOf(false) }
        var enteredName by rememberSaveable { mutableStateOf("") }
        val faceTags = viewModel.faceTags.toMap()

        when(val state = detection){
            is UiState.Failure -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text((state as UiState.Failure).error)
                }
            }
            UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                val detect = (state as UiState.Success<FaceDetections>).data

                detect?.let { item ->
                    EditFaceCanvas(
                        item = item,
                        faceTags = faceTags,
                        onFaceClicked = { index ->
                            selectedFaceIndex = index
                            enteredName = faceTags[index] ?: ""
                            showDialog = true
                        }
                    )
                }

                if (showDialog && selectedFaceIndex != null) {

                    AlertDialog(
                        onDismissRequest = { showDialog = false
                            enteredName = ""
                        },
                        confirmButton = {
                            Button(onClick = {
                                viewModel.tagFace(selectedFaceIndex!!, enteredName)
                                enteredName = ""
                                showDialog = false
                            }) {
                                Text("Save")
                            }
                        },
                        text = {
                            TextField(
                                value = enteredName,
                                onValueChange = { enteredName = it }
                            )
                        }
                    )
                }
            }
        }


    }



    @Composable
    fun EditFaceCanvas(
        item: FaceDetections,
        faceTags: Map<Int, String>,
        onFaceClicked: (Int) -> Unit
    ) {

        val ratio =
            if (item.imageWidth > 0 && item.imageHeight > 0)
                item.imageWidth.toFloat() / item.imageHeight.toFloat()
            else 1f

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio)
        ) {

            AsyncImage(
                model = item.uri,
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds
            )

            Canvas(
                modifier = Modifier
                    .matchParentSize()
                    .pointerInput(item.boxes) {
                        detectTapGestures { tap ->

                            val scaleX = size.width.toFloat() / item.imageWidth.toFloat()
                            val scaleY = size.height.toFloat() / item.imageHeight.toFloat()

                            item.boxes.forEachIndexed { index, rect ->

                                val left = rect.left * scaleX
                                val top = rect.top * scaleY
                                val right = left + rect.width() * scaleX
                                val bottom = top + rect.height() * scaleY

                                if (tap.x in left..right && tap.y in top..bottom) {
                                    onFaceClicked(index)
                                }
                            }
                        }
                    }
            ) {

                val scaleX = size.width / item.imageWidth
                val scaleY = size.height / item.imageHeight

                item.boxes.forEachIndexed { index, rect ->

                    val left = rect.left * scaleX
                    val top = rect.top * scaleY

                    drawRect(
                        color = Color.Red,
                        topLeft = Offset(left, top),
                        size = Size(rect.width() * scaleX, rect.height() * scaleY),
                        style = Stroke(width = 4f)
                    )

                    faceTags[index]?.let { tag ->

                        val textPaint = android.graphics.Paint().apply {
                            isAntiAlias = true
                            textSize = 40f
                            color = android.graphics.Color.WHITE
                            typeface = android.graphics.Typeface.DEFAULT_BOLD
                        }

                        val padding = 12f

                        val textWidth = textPaint.measureText(tag)
                        val fm = textPaint.fontMetrics

                        val bgLeft = left
                        val bgTop = top - (fm.descent - fm.ascent) - padding
                        val bgRight = left + textWidth + padding * 2
                        val bgBottom = top + padding

                        val bgPaint = android.graphics.Paint().apply {
                            color = android.graphics.Color.BLACK
                            isAntiAlias = true
                        }

                        drawContext.canvas.nativeCanvas.drawRoundRect(
                            bgLeft,
                            bgTop,
                            bgRight,
                            bgBottom,
                            12f,
                            12f,
                            bgPaint
                        )

                        drawContext.canvas.nativeCanvas.drawText(
                            tag,
                            bgLeft + padding,
                            bgBottom - padding,
                            textPaint
                        )
                    }
                }
            }
        }
    }


