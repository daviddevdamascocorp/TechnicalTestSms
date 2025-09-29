package com.example.apptextmessdavid.navigation


sealed class Screen (val route:String){
    object Permisson:Screen(route = "permission_screen")
    object Main:Screen(route = "main_screen")
}

