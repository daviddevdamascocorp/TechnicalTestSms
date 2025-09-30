package com.example.apptextmessdavid.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    var receiveSmsPrmission =  rememberPermissionState(android.Manifest.permission.RECEIVE_SMS)
    if(readSmsPrmission.status.isGranted ){
        onPermissionGranted()
    }else{
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            var textShow = if (readSmsPrmission.status.shouldShowRationale){
                "Permission is important for this app, grant the permission"
            } else {
                "Permission required for this application to work. " +
                        "Please grant the permission"

            }
            Text(text = textShow,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 20.dp))
            Button(onClick = { arrayOf(readSmsPrmission.launchPermissionRequest(),
                sendSmsPrmission.launchPermissionRequest(),receiveSmsPrmission.launchPermissionRequest())  }) {
                Text(text = "Request permission")
            }

        }
    }
}

