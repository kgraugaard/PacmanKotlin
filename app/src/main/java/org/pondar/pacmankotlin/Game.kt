package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

/**
 *
 * This class should contain all your game logic
 */

class Game(private var context: Context, view: TextView) {

    private var pointsView: TextView = view
    private var points: Int = 0

    //a reference to the gameview
    private var gameView: GameView? = null
    private var h: Int = 0
    private var w: Int = 0 //height and width of screen

    //bitmap of the pacman
    val pacman = PacMan(context)
    var direction = Direction.none

    //did we initialize the coins?
    var coinsInitialized = false
    var enemiesInitialized = false

    //the list of goldcoins - initially empty
    var coins = ArrayList<GoldCoin>()
    var enemies = ArrayList<Enemy>()

    //is the game on???
/*    var GameWon: Boolean by Delegates.observable(false) {_, oldVal, newVal ->
        if (newVal){
            onGameOver?.invoke(true, "Player won the game")
        }
    }

    var GameOver: Boolean by Delegates.observable(false){_ , oldVal, newVal ->
        if (newVal){
            onGameOver?.invoke(true, "Player Lost the game")
        }
    }

    var onGameOver: ((value: Boolean, msg: String) -> Unit)? = null*/

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

    fun initializeEnemies(){
        for (i in 0..0){
            enemies.add(Enemy(context))
        }
        enemiesInitialized = true
    }


    fun newGame() {
        pacman.x = (w - pacman.width()) / 2
        pacman.y = (h - pacman.height()) / 2 //just some starting coordinates - you can change this.
        //reset the points
        coinsInitialized = false
        enemiesInitialized = false
        coins.clear()
        enemies.clear()
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

        //check for collision on coins
        for (coin in remainingCoins){
            if (pacman.IsCollided(coin)){
                coin.taken = true
                points++
                pointsView.text = "${context.resources.getString(R.string.points)} $points"
            }
        }

        //check for collision on enemies
        for (enemy in enemies){
            if (pacman.IsCollided(enemy)){
                enemy.taken = true
                direction = Direction.none
                Toast.makeText(context, "Game Over. Player Lost", Toast.LENGTH_SHORT).show()
            }
        }

        //game over
        if (remainingCoins.count() == 0 && coinsInitialized)
        {
            direction = Direction.none
            Toast.makeText(context, "Game Over. Player Won", Toast.LENGTH_SHORT).show()
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