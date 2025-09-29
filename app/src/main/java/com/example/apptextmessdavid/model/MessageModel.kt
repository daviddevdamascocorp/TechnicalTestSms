package com.example.apptextmessdavid.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MessageModel(
    val message:String,
    val sender:String,
    val date:Long,
    val read:Boolean,
    val type:Int,
    val thread:Int,
    val service: String

)

fun Long.parsedDate():String{
    val date = Date(this)
    val formatDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return  formatDate.format(date)
}