package org.pondar.pacmankotlin

import android.content.Context
import android.content.res.TypedArray
import android.graphics.BitmapFactory
import kotlin.properties.Delegates
import kotlin.random.Random

class Enemy(context: Context, x: Int, y: Int) : GameActor(context) {

    var taken: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
        //Log.d("taken","New Value $newValue")
        enemycollectSound?.let { playSound(it) }
    }

    init {
        this.x = x
        this.y = y
        setBitmap()
    }

    private fun setBitmap(){
        val images: TypedArray = context.resources.obtainTypedArray(R.array.enemy_images)
        val choice = Random.nextInt(1,4)
        val id = images.getResourceId(choice, R.drawable.enemy1)
        bitmap = BitmapFactory.decodeResource(context.resources, id)
    }
}