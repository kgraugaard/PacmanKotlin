package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception
import java.util.*


/**
 *
 * This class should contain all your game logic
 */

class Game(private var context: Context, view: TextView) {

    private var pointsView: TextView = view
    private var points: Int = 0

    //bitmap of the pacman
    val pacman = PacMan(context)

    var direction = Direction.none

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
        for (i in 0..3) {
            coins.add(GoldCoin(context, w, h))
        }

        coinsInitialized = true
    }


    fun newGame() {
        pacman.x = (w - pacman.width()) / 2
        pacman.y = (h - pacman.height()) / 2 //just some starting coordinates - you can change this.
        //reset the points
        coinsInitialized = false
        coins.clear()
        direction = Direction.none
        points = 0
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
        gameView?.invalidate() //redraw screen
    }

    fun setSize(h: Int, w: Int) {
        this.h = h
        this.w = w
    }

    fun changeDirection(strDirection: String){
        if (strDirection == "moveRight"){
            direction = Direction.rigth
        } else if (strDirection == "moveLeft"){
            direction = Direction.left
        } else if (strDirection == "moveDown") {
            direction = Direction.down
        } else if (strDirection == "moveUp"){
            direction = Direction.up
        } else
        {
            direction = Direction.none
        }
    }

    fun movePacman(pixels: Int) {

        when (direction) {
            Direction.rigth -> {
                if (pacman.x + pixels + pacman.width() < w) {
                    pacman.x = pacman.x + pixels
                }
            }
            Direction.left -> {
                if (pacman.x - pixels > 0) {
                    pacman.x = pacman.x - pixels
                }
            }
            Direction.down -> {
                if (pacman.y + pixels + pacman.height() < h) {
                    pacman.y = pacman.y + pixels
                }
            }
            Direction.up -> {
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

        val remainingCoins = coins.filter { !it.taken }

        if (remainingCoins.count() == 0)
        {
            //game over
            Toast.makeText(context, "Game Over", Toast.LENGTH_SHORT).show()
        }

        for (coin in remainingCoins){
            if (pacman.IsCollided(coin)){
                coin.taken = true
                points++
                pointsView.text = "${context.resources.getString(R.string.points)} $points"
            }
        }
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