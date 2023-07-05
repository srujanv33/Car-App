package com.example.aaostest

import android.content.Intent
import android.service.voice.VoiceInteractionService
import androidx.car.app.CarAppService
import androidx.car.app.Screen
import androidx.car.app.Session
import androidx.car.app.validation.HostValidator

const val READ_ACTION = "com.example.aaostest.ACTION_MESSAGE_READ"
const val REPLY_ACTION = "com.example.aaostest.ACTION_MESSAGE_REPLY"
const val CONVERSATION_ID = "conversation_id"
const val EXTRA_VOICE_REPLY = "extra_voice_reply"
class AppService : CarAppService() {
    override fun createHostValidator(): HostValidator {
        return HostValidator.ALLOW_ALL_HOSTS_VALIDATOR
    }

    override fun onCreateSession(): Session {
        return object : Session() {
            override fun onCreateScreen(intent: Intent): Screen {
                return CarApp(carContext)
            }
        }
    }
}