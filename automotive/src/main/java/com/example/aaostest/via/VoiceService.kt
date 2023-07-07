package com.example.aaostest.via

import android.service.voice.VoiceInteractionService
import com.example.aaostest.CarVoiceInteractionSession
import java.util.Arrays


class VoiceService: VoiceInteractionService() {

    private val SUPPORTED_VOICE_ACTIONS = Arrays.asList<String>(
        CarVoiceInteractionSession.VOICE_ACTION_READ_NOTIFICATION,
        CarVoiceInteractionSession.VOICE_ACTION_HANDLE_EXCEPTION,
        CarVoiceInteractionSession.VOICE_ACTION_REPLY_NOTIFICATION
    )


    override fun onReady() {
        super.onReady()
        //Hotword detector impl
    }

    override fun onGetSupportedVoiceActions(voiceActions: MutableSet<String>): MutableSet<String> {
        val result: MutableSet<String> = HashSet(voiceActions)
        result.retainAll(SUPPORTED_VOICE_ACTIONS)
        return result

    }


}