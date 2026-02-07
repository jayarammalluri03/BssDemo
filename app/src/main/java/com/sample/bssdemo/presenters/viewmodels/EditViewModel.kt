package com.sample.bssdemo.presenters.viewmodels


import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.bssdemo.Utils.FaceDetections
import com.sample.bssdemo.Utils.FaceTag
import com.sample.bssdemo.Utils.UiState
import com.sample.bssdemo.domain.usecases.GetFaceDetectionUseCase
import com.sample.bssdemo.domain.usecases.UpdateFaceDetectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel  @Inject constructor(private val getFaceDetectionUseCase: GetFaceDetectionUseCase, private val updateFaceDetectionUseCase: UpdateFaceDetectionUseCase): ViewModel()  {

    private val _state = MutableStateFlow<UiState<FaceDetections?>>(UiState.Loading)
    val state = _state.asStateFlow()

    val faceTags = mutableStateMapOf<Int, String>()

    fun setDetection(detection: FaceDetections?) {
        viewModelScope.launch {
            _state.value = UiState.Loading
            detection?.uri?.let { uri ->
                getFaceDetectionUseCase(uri.toString()).collect { dbData ->
                    val finalData = dbData ?: detection
                    _state.value = UiState.Success(finalData)
                    faceTags.clear()
                    finalData.faces?.forEachIndexed { index, face ->
                        face.name?.let { faceTags[index] = it }
                    }
                }
            }
        }
    }

    fun tagFace(index: Int, name: String) {

        val state = _state.value
        if (state !is UiState.Success) return

        val current = state.data ?: return

        val updatedFaces = if (current.faces.isNullOrEmpty()) {
            current.boxes.map { FaceTag(rect = it, name = null) }.toMutableList()
        } else {
            current.faces.toMutableList()
        }

        if (index >= updatedFaces.size) return

        updatedFaces[index] = updatedFaces[index].copy(name = name)
        faceTags[index] = name

        val updated = current.copy(faces = updatedFaces)

        _state.value = UiState.Success(updated)

        viewModelScope.launch {
            updateFaceDetectionUseCase(updated)
        }
    }
}


