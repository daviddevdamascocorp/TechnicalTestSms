package com.example.apptextmessdavid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log

class smsReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == "android.provider.Telephony.SMS_RECEIVED")
        {
            val bundle = intent.extras
            if(bundle != null){
                val pdus = bundle.get("pdu") as Array<*>
                pdus.forEach {
                    val smsMessage = SmsMessage.createFromPdu(it as ByteArray)
                    val messageBody = smsMessage.messageBody
                    val sender = smsMessage.originatingAddress
                    Log.d("Sms receiver","received message from $sender, message $messageBody")
                }
            }
        }
    }
}