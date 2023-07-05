package com.example.aaostest

import androidx.car.app.CarContext
import androidx.car.app.CarToast
import androidx.car.app.Screen
import androidx.car.app.model.Action
import androidx.car.app.model.Pane
import androidx.car.app.model.PaneTemplate
import androidx.car.app.model.Row
import androidx.car.app.model.Template
import com.google.assistant.appactions.widgets.AppActionsWidgetExtension

class ChatScreen(carContext: CarContext) : Screen(carContext) {
    override fun onGetTemplate(): Template {
        val row =  Row.Builder().setTitle("This is the chat screen").build()
        val pane = Pane.Builder()
            .addRow(row)
            .build()

        val action = Action.Builder()
            .setOnClickListener {
                getSpeechtoText()
//                msg = response
                screenManager.push(ChatScreen(carContext))

                CarToast.makeText(
                    carContext,
                    "How can you help ?",
                    CarToast.LENGTH_SHORT
                ).show()
            }
            .setTitle("Reply")
            .build()

        return PaneTemplate.Builder(pane)
            .setHeaderAction(Action.BACK)
            .build()
    }

    private fun getSpeechtoText() {
//        val appAct: AppActionsWidgetExtension =
//            AppActionsWidgetExtension.newBuilder(a)
    }

}
