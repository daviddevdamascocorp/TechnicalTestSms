package com.example.apptextmessdavid

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.apptextmessdavid.navigation.SetUpNavGraph
import com.example.apptextmessdavid.ui.theme.AppTextMessDavidTheme
import com.example.apptextmessdavid.ui.theme.redDam

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTextMessDavidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                   // MessageUI(context = LocalContext.current)
                    SetUpNavGraph(navController = navController )
                }
            }
        }
    }
}

@Composable
fun MessageUI(context:Context){
    val phoneNumber = remember {
        mutableStateOf("")
    }

    val textMessage = remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

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
        Spacer(modifier = Modifier.height(20.dp))
      TextField(value = textMessage.value,
          onValueChange = {textMessage.value = it},
          placeholder = { Text(text = "Write")},
          modifier = Modifier
              .padding(16.dp)
              .fillMaxWidth(),
          singleLine = true)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            try {
                val smsManager:SmsManager
                if (Build.VERSION.SDK_INT>=23){
                    smsManager = context.getSystemService(SmsManager::class.java)
                }else{
                    smsManager = SmsManager.getDefault()
                }
                //
                smsManager.sendTextMessage(phoneNumber.value,null,textMessage.value,null,null)
                Toast.makeText(context,"Sent",Toast.LENGTH_LONG).show()
            }catch (ex:Exception){
                Toast.makeText(context,"Error" + ex.message,Toast.LENGTH_LONG).show()
                println(ex.message)
            }


        }) {
            Text(text = "Send",
                modifier = Modifier.padding(15.dp),
                color = Color.White,
                fontSize = 15.sp)


        }

}}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTextMessDavidTheme {

    }
}