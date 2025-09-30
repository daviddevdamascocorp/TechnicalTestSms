package com.example.apptextmessdavid.navigation

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.apptextmessdavid.components.ui.MainScreen
import com.example.apptextmessdavid.components.ui.MessageMainView
import com.example.apptextmessdavid.components.ui.PermissionScreen

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Permisson.route
){
    NavHost(navController = navController, startDestination =startDestination ) {
        composable(route = startDestination){
            PermissionScreen( onPermissionGranted = {
                navController.popBackStack()
                navController.navigate(Screen.Main.route)
            })
        }
        composable(route = Screen.Main.route){
            MessageMainView()
        }

    }
    
}