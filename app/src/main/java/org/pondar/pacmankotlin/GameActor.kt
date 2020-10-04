package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.media.AudioManager
import android.media.MediaPlayer

open class GameActor(val context: Context) {

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

    internal fun playSound(resourceId: Int){
        Thread {
            val mp: MediaPlayer = MediaPlayer.create(context,resourceId)
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mp.isLooping = false
            mp.start()

            while (mp.isPlaying){

            }
        }.start()
    }
}