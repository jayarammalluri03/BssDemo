package com.sample.bssdemo.presenters.viewmodels

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.vision.facedetector.FaceDetector
import com.sample.bssdemo.Utils.Constants
import com.sample.bssdemo.Utils.FaceDetections
import com.sample.bssdemo.Utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(@ApplicationContext private val context: Context, private val faceDetector: FaceDetector): ViewModel() {


    private val _images = MutableStateFlow<UiState<List<FaceDetections>>>(UiState.Loading)

    val images: StateFlow<UiState<List<FaceDetections>>> = _images


    init {
        loadImages()
    }
    fun loadImages() {
        viewModelScope.launch(Dispatchers.IO) {
            val images=  fetchGalleryImages(context)
            Log.e("resultsGot",images.size.toString())
            if (images.isEmpty()) {
                _images.value = UiState.Failure("No images found")
                return@launch
            }
            Log.e("resultsgot", images.size.toString())
            val results = mutableListOf<FaceDetections>()
            for (uri in images){
                try {
                    val bitmap = Constants.uriToBitmap(context, uri)
                    //    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 640, 640, true)
                    val argbBitmap = Constants.convertToArgb8888(bitmap)
                    val mpImage = BitmapImageBuilder(argbBitmap).build()
                    val result = faceDetector.detect(mpImage)
                    val boxes = result.detections().map {
                        it.boundingBox()
                    }
                    Log.e("resultsgot", boxes.size.toString())
                    if(boxes.isNotEmpty()){
                        val item = FaceDetections(
                            uri = uri,
                            faceCount = boxes.size,
                            boxes = boxes,
                            imageWidth = bitmap.width,
                            imageHeight = bitmap.height,
                            faces = emptyList()
                        )
                        results.add(item)
                        _images.value = UiState.Success(results.toList())
                        Log.e("resultsgot", boxes.size.toString())
                    }
                }catch (e: Exception){
                    Log.e("ImageDecode", "Skipping bad image: $uri", e)
                }
            }
                if(results.isNotEmpty())
            _images.value = UiState.Success(results.toList())
            else
                    _images.value = UiState.Failure("No images found")
        }
    }

    private fun fetchGalleryImages(context: Context): List<Uri> {

        val imageList = mutableListOf<Uri>()

        try {

            val projection = arrayOf(MediaStore.Images.Media._ID)

            context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
            )?.use { cursor ->

                val idColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)

                    val uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    imageList.add(uri)
                }
            }

        } catch (e: SecurityException) {

            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return imageList
    }





}