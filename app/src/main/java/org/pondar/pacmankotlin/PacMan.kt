package org.pondar.pacmankotlin

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.util.Log
import java.lang.Exception

class PacMan(context: Context) : GameActor(context) {

    init {
        this.bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman)
    }

    fun IsCollided(foreignObject: GameActor) : Boolean {

        val foreignRect = foreignObject.getRect()

        if (this.getRect().intersect(foreignRect)){
            val collisionBounds = getCollisionBounds(foreignRect)
            for (i in collisionBounds!!.left until collisionBounds!!.right) {
                for (j in collisionBounds!!.top until collisionBounds!!.bottom) {
                    try {
                        val thisBitmapPixel: Int = this.bitmap!!.getPixel(i - this.x, j - this.y)
                        val foreignBitMapPixel: Int = foreignObject.bitmap!!.getPixel(i - foreignObject.x, j - foreignObject.y)
                        if (isFilled(thisBitmapPixel) && isFilled(foreignBitMapPixel)){
                            Log.d("Collision", true.toString())
                            return true
                        }
                    }
                    catch (ex: Exception){
                        return false
                    }
                }
            }
        }

        return false
    }

    private fun getCollisionBounds(foreignRect: Rect): Rect? {
        return Rect(Math.max(getRect().left, foreignRect.left), Math.max(getRect().top, foreignRect.top), Math.min(getRect().right, foreignRect.right), Math.min(getRect().bottom, foreignRect.bottom))
    }

    private fun isFilled(pixel: Int): Boolean {
        return pixel != Color.TRANSPARENT
    }
}