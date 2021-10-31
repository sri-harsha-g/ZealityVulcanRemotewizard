package com.famas.frontendtask.feature_face_auth.presentation

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.Surface.ROTATION_180
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.io.File

fun startCamera(
    context: Context,
    previewView: PreviewView?,
    setImageCapture: (ImageCapture) -> Unit,
    lifecycleOwner: LifecycleOwner
) {
    val cameraProviderFeature = ProcessCameraProvider.getInstance(context)

    cameraProviderFeature.addListener({
        val cameraProvider = cameraProviderFeature.get()

        val preview = Preview.Builder().build()
            .also {
                it.setSurfaceProvider(previewView?.surfaceProvider)
            }

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        val imageCapture = ImageCapture.Builder().build()
        setImageCapture(imageCapture)

        try {
            //Unbinding to init safe for safe binding
            cameraProvider.unbindAll()

            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
        } catch (e: Exception) {
            Log.d("myTag", "exception at binding camera", e)
        }

    }, ContextCompat.getMainExecutor(context))
}

fun takePhoto(
    context: Context,
    imageCapture: ImageCapture?,
    showSnackbar: (String) -> Unit,
    onImageSaved: (File) -> Unit,
    onError: () -> Unit
) {
    Log.d("myTag", "image capture is: $imageCapture")
    imageCapture ?: return

    val photoFile = File.createTempFile("${System.currentTimeMillis()}", ".jpg")

    val outputOptions = ImageCapture.OutputFileOptions
        .Builder(photoFile)
        .build()

    imageCapture
        .takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savePath = photoFile.absolutePath
                    val msg = "Photo capture succeeded: $savePath"
                    showSnackbar(msg)
                    onImageSaved(photoFile)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("myTag", "Photo capture failed: ${exception.message}", exception)
                    exception.message?.let { showSnackbar(it) }
                }

            }
        )
}