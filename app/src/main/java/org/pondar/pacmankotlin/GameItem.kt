package org.pondar.pacmankotlin

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect

open class GameItem(val resources: Resources) {

    var x: Int = 0
    var y: Int = 0

    var bitmap: Bitmap? = null

    fun getRect(): Rect
    {
        return Rect(x, y, y + bitmap!!.width, y + bitmap!!.height)
    }

    fun height(): Int{
        return  this.bitmap!!.height
    }

    fun width(): Int
    {
        return this.bitmap!!.width
    }
}