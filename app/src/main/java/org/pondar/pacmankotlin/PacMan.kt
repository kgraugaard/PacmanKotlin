package org.pondar.pacmankotlin

import android.content.res.Resources
import android.graphics.BitmapFactory

class PacMan(resources: Resources) : GameItem(resources) {

    init {
        this.bitmap = BitmapFactory.decodeResource(resources, R.drawable.pacman)
    }
}