package org.pondar.pacmankotlin

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Bundle
import kotlin.random.Random


//Here you need to fill out what should be in a GoldCoin and what should the constructor be
class GoldCoin(val resources: Resources, val maxX: Int, val maxY: Int) {

    var X: Int = Random.nextInt(0, maxX)
    var Y: Int = Random.nextInt(0, maxY)

    var Icon: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.coin20x20)

    var taken: Boolean = false

    fun getRect(): Rect
    {
        return Rect(X, Y, X + Icon.width, Y + Icon.height)
    }

}