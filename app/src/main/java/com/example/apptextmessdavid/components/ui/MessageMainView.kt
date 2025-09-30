package com.example.apptextmessdavid.components.ui

import android.content.Context
import android.os.Build
import android.provider.Telephony
import android.telephony.SmsManager
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apptextmessdavid.model.ListSmsModel
import com.example.apptextmessdavid.model.MessageModel
import com.example.apptextmessdavid.model.parsedDate
import com.example.apptextmessdavid.ui.theme.redDam

@Composable
fun MessageMainView(){
    val context = LocalContext.current
    var allMessages by remember {
        mutableStateOf<List<ListSmsModel>>(emptyList())
    }

    LaunchedEffect(Unit) {
        allMessages = GetMessagees(context)
    }
    Column (modifier = Modifier.fillMaxWidth().padding(4.dp)){
        SendMessageUI(context)
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn {
            items(allMessages){
                message->
                MessageCard(message)
            }
        }
    }

}
@Composable
fun MessageCard(message: ListSmsModel){
    Card (modifier = Modifier
        .fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)

        ){

        Column(modifier = Modifier.padding(7.dp)) {
            Text(
                text = message.sender,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message.message,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message.date.parsedDate().split(" ").toString(),
                fontSize = 13.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

fun GetMessagees(context: Context):List<ListSmsModel>{
    val smsList = mutableListOf<ListSmsModel>()
    val cursor = context.contentResolver.query(
        Telephony.Sms.CONTENT_URI,
        arrayOf(
            Telephony.Sms.ADDRESS,
            Telephony.Sms.BODY,
            Telephony.Sms.DATE
        ),
        null,
        null,
        Telephony.Sms.DEFAULT_SORT_ORDER
    )

    cursor?.use {
        val indexMessage = it.getColumnIndex("body")
        val indexSender = it.getColumnIndex("address")
        val indexDate = it.getColumnIndex("date")
        while (it.moveToNext()){   smsList.add(
            ListSmsModel(
                message = it.getString(indexMessage),
                sender =  it.getString(indexSender),
                date = it.getLong(indexDate),
            )

        )
        }

    }
    return smsList
}

@Composable
fun SendMessageUI(context:Context){
    val phoneNumber = remember {
        mutableStateOf("")
    }

    val textMessage = remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(all = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {
        Text(text = "Sms client with android",
            color = redDam,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold )

        TextField(value = phoneNumber.value,
            onValueChange = {phoneNumber.value = it},
            placeholder = { Text(text="Phone number to text") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(5.dp))
        TextField(value = textMessage.value,
            onValueChange = {textMessage.value = it},
            placeholder = { Text(text = "Write")},
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            singleLine = true)
        Spacer(modifier = Modifier.height(5.dp))
        Button(onClick = {
            try {
                val smsManager: SmsManager
                if (Build.VERSION.SDK_INT>=23){
                    smsManager = context.getSystemService(SmsManager::class.java)
                }else{
                    smsManager = SmsManager.getDefault()
                }
                //
                smsManager.sendTextMessage(phoneNumber.value,null,textMessage.value,null,null)
                Toast.makeText(context,"Sent", Toast.LENGTH_LONG).show()
            }catch (ex:Exception){
                Toast.makeText(context,"Error" + ex.message, Toast.LENGTH_LONG).show()
                println(ex.message)
            }


        }) {
            Text(text = "Send",
                modifier = Modifier.padding(15.dp),
                color = Color.White,
                fontSize = 15.sp)


        }

    }}