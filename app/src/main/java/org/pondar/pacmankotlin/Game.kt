package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.util.Log
import android.widget.TextView
import java.util.*


/**
 *
 * This class should contain all your game logic
 */

class Game(private var context: Context, view: TextView) {

    private var pointsView: TextView = view
    private var points: Int = 0

    //bitmap of the pacman
    val pacman = PacMan(context.resources)
    var pacmanDirection: Direction = Direction.stopped


    //did we initialize the coins?
    var coinsInitialized = false

    //the list of goldcoins - initially empty
    var coins = ArrayList<GoldCoin>()

    //a reference to the gameview
    private var gameView: GameView? = null
    private var h: Int = 0
    private var w: Int = 0 //height and width of screen


    init {
        newGame()
    }

    fun setGameView(view: GameView) {
        this.gameView = view
    }

    //TODO initialize goldcoins also here
    fun initializeGoldcoins() {
        //DO Stuff to initialize the array list with coins.
        for (i in 0..0) {
            coins.add(GoldCoin(context.resources, w, h))
        }

        coinsInitialized = true
    }


    fun newGame() {
        pacman.x = (w - pacman.width()) / 2
        pacman.y = (h - pacman.height()) / 2 //just some starting coordinates - you can change this.
        //reset the points
        coinsInitialized = false
        coins.clear()
        points = 0
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
        gameView?.invalidate() //redraw screen
    }

    fun setSize(h: Int, w: Int) {
        this.h = h
        this.w = w
    }

    fun movePacman(pixels: Int, direction: String) {

        when (direction) {
            "moveRight" -> {
                if (pacman.x + pixels + pacman.width() < w) {
                    pacman.x = pacman.x + pixels
                }
            }
            "moveLeft" -> {
                if (pacman.x - pixels > 0) {
                    pacman.x = pacman.x - pixels
                }
            }
            "moveDown" -> {
                if (pacman.y + pixels + pacman.height() < h) {
                    pacman.y = pacman.y + pixels
                }
            }
            "moveUp" -> {
                if (pacman.y - pixels > 0) {
                    pacman.y = pacman.y - pixels
                }
            }
        }

        doCollisionCheck()
        gameView!!.invalidate()

    }

    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    fun doCollisionCheck() {
        val firstCoin = coins[0]


        /*
        val pacmanRect: Rect = Rect(pacman.x, pacman.y, pacman.x + pacBitmap.width, pacman.y + pacBitmap.height);
        val coinRect: Rect = firstCoin.getRect()



        if (pacmanRect.intersect(coinRect)){
            val collisionBounds = getCollisionBounds(pacmanRect, coinRect)

            for (i in collisionBounds!!.left until collisionBounds!!.right) {
                for (j in collisionBounds!!.top until collisionBounds!!.bottom) {

                    val bitmap1Pixel: Int = pacBitmap.getPixel(i - pacman.x, j - pacman.y)
                    val bitmap2Pixel: Int = firstCoin.Icon.getPixel(i - firstCoin.X, j - firstCoin.Y)
                    if (isFilled(bitmap1Pixel) && isFilled(bitmap2Pixel)) {
                        Log.d("Collision", true.toString())
                    }
                }
            }

        }*/

        //Log.d("Rect", pacmanRect.flattenToString() + ": Coin: " + firstCoin.getRect().flattenToString() + ". Intersect: " + pacmanRect.intersect(coinRect).toString())
    }

    private fun getCollisionBounds(rect1: Rect, rect2: Rect): Rect? {
        return Rect(Math.max(rect1.left, rect2.left), Math.max(rect1.top, rect2.top), Math.min(rect1.right, rect2.right), Math.min(rect1.bottom, rect2.bottom))
    }

    private fun isFilled(pixel: Int): Boolean {
        return pixel != Color.TRANSPARENT
    }


    @Deprecated("Use movePacman instead")
    fun movePacmanRight(pixels: Int) {
        //still within our boundaries?
        if (pacman.x + pixels + pacman.width() < w) {
            pacman.x = pacman.x + pixels
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }
}