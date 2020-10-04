package org.pondar.pacmankotlin

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import kotlin.properties.Delegates
import kotlin.random.Random


//Here you need to fill out what should be in a GoldCoin and what should the constructor be
class GoldCoin(context: Context, maxX: Int, maxY: Int) : GameActor(context) {

    var taken: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
        Log.d("taken","New Value $newValue")
        playSound(R.raw.coincollect)
    }

    /*var taken: Boolean = false*/

    init {
        x = Random.nextInt(0, maxX)
        y = Random.nextInt(0, maxY)
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.coin20x20)
    }
}