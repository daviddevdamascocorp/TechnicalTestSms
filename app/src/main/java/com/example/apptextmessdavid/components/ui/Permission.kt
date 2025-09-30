package com.example.apptextmessdavid.components.ui

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@Composable
fun PermissionScreen(onPermissionGranted:()->Unit){
    RequestSmsPermission(onPermissionGranted = onPermissionGranted)
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun RequestSmsPermission(onPermissionGranted: () -> Unit){
    var readSmsPrmission = rememberPermissionState(
        android.Manifest.permission.READ_SMS

    )
    var sendSmsPrmission =  rememberPermissionState(android.Manifest.permission.SEND_SMS)
    if(readSmsPrmission.status.isGranted ){
        onPermissionGranted()
    }else{
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                val allGranted = permissions.values.all { it }
                if (allGranted) {
                    onPermissionGranted()
                }
            }

            LaunchedEffect(Unit) {
                launcher.launch(
                    arrayOf(
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.SEND_SMS
                    )
                )
            }

        }
    }
}

