package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlin.properties.Delegates

class Enemy(context: Context) : GameActor(context) {

    var taken: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
        //Log.d("taken","New Value $newValue")
        playSound(R.raw.enemycollect)
    }

    init {
        x = 50
        y = 150
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.enemy1)
    }
}