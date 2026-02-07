package com.sample.bssdemo.presenters.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sample.bssdemo.Utils.FaceDetections
import com.sample.bssdemo.Utils.UiState
import com.sample.bssdemo.presenters.component.FaceImageItem
import com.sample.bssdemo.presenters.viewmodels.HomeViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel, onclick: (FaceDetections) -> Unit) {

    val state by homeViewModel.images.collectAsState()

    when (state) {

        UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text((state as UiState.Failure).error)
            }
        }

        is UiState.Success -> {

            val list = (state as UiState.Success<List<FaceDetections>>).data

            LazyColumn {
                items(list) { item ->
                    FaceImageItem(
                        item,
                        onclick = { onclick(item) },
                        onSelectedFaceIndex = { onclick(item) }
                    )
                }
            }
        }
    }
}

