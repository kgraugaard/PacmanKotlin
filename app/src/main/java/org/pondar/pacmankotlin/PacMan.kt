package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.util.Log
import java.lang.Exception


class PacMan(context: Context) : GameActor(context) {

    init {
        this.bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman_small)
    }

    fun IsCollided(foreignObject: GameActor): Boolean {

        val pacBounds = Rect(this.x, this.y, this.x + this.bitmap!!.width, this.y + this.bitmap!!.height)
        val foreignBoumds = Rect(foreignObject.x, foreignObject.y, foreignObject.x + foreignObject.bitmap!!.width, foreignObject.y + foreignObject.bitmap!!.height)

        if (Rect.intersects(pacBounds, foreignBoumds)) {
            val collisionBounds = getCollisionBounds(pacBounds, foreignBoumds)
            for (i in collisionBounds!!.left until collisionBounds.right) {
                for (j in collisionBounds.top until collisionBounds.bottom) {
                    try {
                        val bitmap1Pixel = this.bitmap!!.getPixel(i - this.x, j - this.y)
                        val bitmap2Pixel = foreignObject.bitmap!!.getPixel(i - foreignObject.x, j - foreignObject.y)
                        if (isFilled(bitmap1Pixel) && isFilled(bitmap2Pixel)) {
                            Log.d("Collision", "${true}")
                            return true
                        }
                    } catch (ex: Exception){
                        return  false
                    }

                }
            }
        }
        return false
    }

    private fun getCollisionBounds(rect1: Rect, rect2: Rect): Rect? {
        val left = Math.max(rect1.left, rect2.left) as Int
        val top = Math.max(rect1.top, rect2.top) as Int
        val right = Math.min(rect1.right, rect2.right) as Int
        val bottom = Math.min(rect1.bottom, rect2.bottom) as Int
        return Rect(left, top, right, bottom)
    }

    private fun isFilled(pixel: Int): Boolean {
        return pixel != Color.TRANSPARENT
    }
}