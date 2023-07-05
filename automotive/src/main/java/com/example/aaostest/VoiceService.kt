package com.example.aaostest

import android.service.voice.VoiceInteractionService
import java.util.Arrays


class VoiceService: VoiceInteractionService() {

    private val SUPPORTED_VOICE_ACTIONS = Arrays.asList<String>(
//        CarVoiceInteractionSession.VOICE_ACTION_READ_NOTIFICATION,
//        CarVoiceInteractionSession.VOICE_ACTION_REPLY_NOTIFICATION,
//        CarVoiceInteractionSession.VOICE_ACTION_HANDLE_EXCEPTION
    )


    override fun onGetSupportedVoiceActions(voiceActions: MutableSet<String>): MutableSet<String> {
        val result: MutableSet<String> = HashSet(voiceActions)
        result.retainAll(SUPPORTED_VOICE_ACTIONS)
        return result

    }


}