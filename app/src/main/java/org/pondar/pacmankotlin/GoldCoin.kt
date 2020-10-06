package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import kotlin.properties.Delegates
import kotlin.random.Random


//Here you need to fill out what should be in a GoldCoin and what should the constructor be
class GoldCoin(context: Context, maxX: Int, maxY: Int) : GameActor(context) {

    var taken: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
        Log.d("taken","New Value $newValue")
        coincollectSound?.let { playSound(it) }
    }

    init {
        x = Random.nextInt(150, maxX)
        y = Random.nextInt(150, maxY)
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.coin20x20)
    }
}