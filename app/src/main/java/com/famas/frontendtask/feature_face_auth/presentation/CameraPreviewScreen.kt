package com.famas.frontendtask.feature_face_auth.presentation

import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberImagePainter
import com.famas.frontendtask.R
import com.famas.frontendtask.core.ui.theme.SpaceLarge
import com.famas.frontendtask.core.util.cameraPermissionsGranted
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private lateinit var cameraExecutor: ExecutorService

@Composable
fun CameraPreviewScreen(
    navigate: (String) -> Unit,
    showSnackbar: (String) -> Unit
) {
    val context = LocalContext.current
    var startLaunchedEffect by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    var previewView: PreviewView? = remember { null }
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            if (it && !startLaunchedEffect) {
                startLaunchedEffect = true
            } else Toast.makeText(
                context,
                context.getText(R.string.except_cam_perm),
                Toast.LENGTH_SHORT
            ).show()
        }
    )
    var capturedFile by remember {
        mutableStateOf<File?>(null)
    }
    var capturing by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = startLaunchedEffect, block = {
        Log.d("myTag", "launched effect started")
        cameraExecutor = Executors.newSingleThreadExecutor()
        if (cameraPermissionsGranted(context)) startCamera(
            context = context,
            previewView = previewView,
            setImageCapture = { imageCapture = it },
            lifecycleOwner = lifecycleOwner
        )
        else launcher.launch(Manifest.permission.CAMERA)
    })


    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { PreviewView(context) },
            modifier = Modifier.fillMaxSize()
        ) {
            previewView = it
        }

        if (capturedFile != null) {
            Image(
                painter = rememberImagePainter(data = capturedFile),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = SpaceLarge),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if (capturedFile != null) {
                Button(onClick = { capturedFile = null }) {
                    Text(text = "retake")
                }
            }

            if (capturing) {
                CircularProgressIndicator()
            } else Button(
                onClick = {
                    if (!cameraPermissionsGranted(context)) launcher.launch(Manifest.permission.CAMERA)
                    else {
                        if (capturedFile == null) {
                            capturing = true
                            takePhoto(
                                context = context,
                                imageCapture = imageCapture,
                                showSnackbar = {
                                    //showSnackbar()
                                },
                                onError = { capturing = false },
                                onImageSaved = {
                                    capturedFile = it
                                    capturing = false
                                }
                            )
                        } else {
                            showSnackbar("verifying..")
                        }
                    }
                },
                enabled = !capturing
            ) {
                Text(text = if (capturedFile == null) "take photo" else "verify")
            }
        }
    }


//Use destroy in viewmodel to shutdown camera with cameraExecutor.shutdown()
}
