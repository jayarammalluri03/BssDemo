package com.sample.bssdemo.presenters.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.bssdemo.Utils.FaceDetections
import com.sample.bssdemo.Utils.UiState
import com.sample.bssdemo.domain.usecases.GetAllFaceDetectionsUseCase
import com.sample.bssdemo.domain.usecases.InsertFaceDetectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val getAllFaceDetectionsUseCase: GetAllFaceDetectionsUseCase,
    private val insertFaceDetectionUseCase: InsertFaceDetectionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<FaceDetections>>>(UiState.Loading)
    val state: StateFlow<UiState<List<FaceDetections>>> = _state

    init {
        observeSavedFaces()
    }

    private fun observeSavedFaces() {
        viewModelScope.launch {
            try {
                getAllFaceDetectionsUseCase().collect {
                    if(it.isNotEmpty())
                    _state.value = UiState.Success(it)
                    else
                        _state.value= UiState.Failure("No images Found")
                }
            }catch (e: Exception){
                _state.value = UiState.Failure(e.message.toString())
            }

        }
    }

    fun insert(data: FaceDetections) {
        viewModelScope.launch {
            insertFaceDetectionUseCase(data)
        }
    }
}
