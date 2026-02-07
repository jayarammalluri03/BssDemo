package com.sample.bssdemo.presenters.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sample.bssdemo.Utils.FaceDetections
import com.sample.bssdemo.Utils.UiState
import com.sample.bssdemo.presenters.component.FaceImageItem
import com.sample.bssdemo.presenters.viewmodels.HomeViewModel
import com.sample.bssdemo.presenters.viewmodels.SavedViewModel

    @Composable
    fun SavedScreen(savedViewModel: SavedViewModel, onclick: (FaceDetections) -> Unit) {

        val images by savedViewModel.state.collectAsState()

        when(val state= images){
            is UiState.Failure -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text((state as UiState.Failure).error)
                }
            }
            UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }
            is UiState.Success<*> -> {
                val imagesData = (state as UiState.Success).data
                LazyColumn {
                    items(imagesData) { item ->
                        FaceImageItem(item,onclick = {
                            onclick(item)
                        }, onSelectedFaceIndex = {onclick(item)})
                    }
                }
            }
        }

    }

