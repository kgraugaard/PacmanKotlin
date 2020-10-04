package org.pondar.pacmankotlin

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Bundle
import kotlin.random.Random


//Here you need to fill out what should be in a GoldCoin and what should the constructor be
class GoldCoin(resources: Resources, maxX: Int, maxY: Int) : GameItem(resources) {

    var taken: Boolean = false

    init {
        x = Random.nextInt(0, maxX)
        y = Random.nextInt(0, maxY)
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.coin20x20)
    }

}