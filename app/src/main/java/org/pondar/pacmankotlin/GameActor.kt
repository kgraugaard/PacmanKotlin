package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool


open class GameActor(val context: Context) {

    private val soundPool: SoundPool = SoundPool(4, AudioManager.STREAM_MUSIC, 100)

    internal var coincollectSound: Int? = null
    internal var enemycollectSound: Int? = null

    var x: Int = 0
    var y: Int = 0

    var bitmap: Bitmap? = null

    init {
        coincollectSound = soundPool.load(context, R.raw.coincollect, 1)
        enemycollectSound = soundPool.load(context, R.raw.enemycollect, 2)
    }

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

    internal fun playSound(resourceId: Int){

        soundPool.play(resourceId, 1F,1F,10, 0,1F)

    }
}