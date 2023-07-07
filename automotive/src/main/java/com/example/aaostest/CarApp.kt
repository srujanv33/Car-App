package com.example.aaostest

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.SpeechRecognizer.RESULTS_RECOGNITION
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.annotation.RequiresApi
import androidx.car.app.CarContext
import androidx.car.app.CarToast
import androidx.car.app.Screen
import androidx.car.app.model.Action
import androidx.car.app.model.ActionStrip
import androidx.car.app.model.MessageTemplate
import androidx.car.app.model.Template
import androidx.core.app.NotificationManagerCompat
import java.util.Locale


class CarApp(carContext: CarContext) : Screen(carContext) {
    var msg = "Hey! I am Wysa...\n I am here to help you destress and nurture yourself."
    private lateinit var mNotificationManager: NotificationManagerCompat
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var recognizer: RecognitionListener
    private var speaking: Boolean = false
    private var count: Int = 0
    lateinit var action: Action

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onGetTemplate(): Template {
        mNotificationManager = NotificationManagerCompat.from(carContext)

        setupSpeechRecognition()

        action = Action.Builder()
            .setOnClickListener {
                textToSpeech()
//                screenManager.push(ChatScreen(carContext))

                CarToast.makeText(
                    carContext,
                    "How can you help ?",
                    CarToast.LENGTH_SHORT
                ).show()
            }
            .setTitle("Reply")
            .build()

        val actionStrip = ActionStrip.Builder()
        actionStrip.addAction(action)


        return MessageTemplate.Builder(msg)
            .setActionStrip(actionStrip.build())
            .build()
//        return PaneTemplate.Builder(
//            Pane.Builder()
//                .addRow(row)
//                .build()
//        )
//            .setActionStrip(actionStrip.build())
//            .setHeaderAction(Action.APP_ICON).build()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setupSpeechRecognition() {
        speechRecognizer = SpeechRecognizer.createOnDeviceSpeechRecognizer(carContext)
        recognizer = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                TODO("Not yet implemented")
            }

            override fun onBeginningOfSpeech() {
                TODO("Not yet implemented")
            }

            override fun onRmsChanged(rmsdB: Float) {
                TODO("Not yet implemented")
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                TODO("Not yet implemented")
            }

            override fun onEndOfSpeech() {
                TODO("Not yet implemented")
            }

            override fun onError(error: Int) {
                TODO("Not yet implemented")
            }

            override fun onResults(results: Bundle?) {
                if (results != null) {
                    val speechList = results.getStringArrayList(RESULTS_RECOGNITION)
                    CarToast.makeText(carContext, speechList!![0], CarToast.LENGTH_SHORT).show()
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                TODO("Not yet implemented")
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                TODO("Not yet implemented")
            }
        }

        speechRecognizer.setRecognitionListener(recognizer)
    }

    private fun textToSpeech() {
        val list = mutableListOf<String>()
        list.add("Hey I am Wysa.")
        list.add("I am here to help you de-stress and nurture yourself")
        list.add("I am an AI penguin you can chat with about your emotions and thoughts.")
        textToSpeech = TextToSpeech(
            carContext
        ) { status ->
            if (status == TextToSpeech.SUCCESS)
                textToSpeech.language = Locale.UK

            for (s in list) {
                textToSpeech.speak(s, TextToSpeech.QUEUE_ADD, null, "")
                count++
                if (count.equals(list.size))
                    speaking = false
            }

            textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    speaking = true
                }

                override fun onDone(utteranceId: String?) {

                    startListening()

//                   if (!speaking)
//                       CarToast.makeText(
//                           carContext,
//                           "Start speaking...",
//                           CarToast.LENGTH_SHORT
//                       ).show()
                }

                override fun onError(utteranceId: String?) {
                    speaking = false
                    textToSpeech.stop()
                }
            })
        }
    }

    private fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 100000000)
        try {
            speechRecognizer.startListening(intent)
        } catch (e: SecurityException) {
            CarToast.makeText(
                carContext,
                "Google voice typing must be enabled!",
                CarToast.LENGTH_SHORT
            )
                .show()
        }
    }


    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            ActivityCompat.requestPermissions(this ,String[]{Manifest.permission.RECORD_AUDIO},225)
        }
    }
}