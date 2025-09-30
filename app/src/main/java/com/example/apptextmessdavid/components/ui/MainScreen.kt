package com.example.apptextmessdavid.components.ui

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.example.apptextmessdavid.model.MessageModel
import com.example.apptextmessdavid.model.parsedDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(){
    val context = LocalContext.current
    val allMessages = remember {
        mutableStateMapOf<String,List<MessageModel>>()
    }
    LaunchedEffect(key1 = Unit) {
        val messages = readMessages(context, type = "inbox") +
                readMessages(context = context, type = "sent")
        allMessages+=messages.sortedBy { it.date }.groupBy { it.sender }

    }
    LazyColumn (modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)){
        allMessages.forEach{
            (sender,messages)->
                stickyHeader(key = sender) {
                    SendView(sender = sender)
                }
                messages.groupBy { it.date.parsedDate().split(" ").first()
                }.forEach{
                    (date,messages)->
                    item{
                        Text(modifier = Modifier.fillMaxWidth().alpha(0.38f),
                            text = date,
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            color = MaterialTheme.colorScheme.onSurface)
                    }
                    items(
                        items = messages,
                        key = {it.date}
                    ){
                        MessageView(message = it)
                    }
                }
        }
    }
}

private  fun readMessages(context:Context, type:String):List<MessageModel>{
    val messages = mutableListOf<MessageModel>()
    val cursor = context.contentResolver.query(
        Uri.parse("content://sms/$type"),
        null,
        null,
        null,
        null
    )
    cursor?.use {
        val indexMessage = it.getColumnIndex("body")
        val indexSender = it.getColumnIndex("address")
        val indexDate = it.getColumnIndex("date")
        val indexRead  = it.getColumnIndex("read")
        val indexType = it.getColumnIndex("type")
        val indexThread = it.getColumnIndex("thread_id")
        val indexService = it.getColumnIndex("service_center")
        while (it.moveToNext()){
            messages.add(
                MessageModel(
                    message = it.getString(indexMessage),
                    sender =  it.getString(indexSender),
                    date = it.getLong(indexDate),
                    read = it.getString(indexRead).toBoolean(),
                    type = it.getInt(indexType),
                    thread =  it.getInt(indexThread),
                    service = it.getString(indexService) ?:""

                )
            )
        }

    }
    return  messages
}