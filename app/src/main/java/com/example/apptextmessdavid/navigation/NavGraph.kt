package com.example.apptextmessdavid.navigation

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Permisson.route
){
    NavHost(navController = navController, startDestination =startDestination ) {
        composable(route = startDestination){

        }
        composable(route = Screen.Main.route){

        }

    }
    
}