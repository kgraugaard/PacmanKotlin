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

class Game(private var context: Context) {

    private var points: Int = 0
    private var level : Int = 1


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

    var numberOfCoins: Int = 3
    var numberOfEnemies: Int = 0

    //is the gaming still runnin
    var onGameRunning: ((value: Boolean) -> Unit)? = null

    //point scored
    var onPoint: ((value: Int) -> Unit)? = null

    //set level change
    var onChangeLevel: ((value: Int) -> Unit)? = null

    init {
        newGame()
    }

    fun setGameView(view: GameView) {
        this.gameView = view
    }

    //TODO initialize goldcoins also here
    fun initializeGoldcoins() {
        //DO Stuff to initialize the array list with coins.
        for (i in 0..numberOfCoins) {
            coins.add(GoldCoin(context, w - 150, h - 150))
        }

        coinsInitialized = true
    }

    fun initializeEnemies(){
        for (i in 0..numberOfEnemies){

            var x: Int = 50
            var y: Int = 200

            when (i){
                1 -> {x = w - 100; y = 200}
                2 -> {x = 50; y = h  -250}
                3 -> {x = w - 100; y = h - 250}
            }
            enemies.add(Enemy(context, x, y))
        }
        enemiesInitialized = true
    }

    private fun initNewGame(){
        pacman.x = (w - pacman.width()) / 2
        pacman.y = (h - pacman.height()) / 2 //just some starting coordinates - you can change this.

        coinsInitialized = false
        enemiesInitialized = false

        coins.clear()
        enemies.clear()

        direction = Direction.none
        onGameRunning?.let { it(true) }
        gameView?.invalidate() //redraw screen
    }

    fun newGame() {
        //reset the points and other stuff
        level = 1
        numberOfCoins =  3
        numberOfEnemies = 0
        points = 0
        onPoint?.let { it(points) }
        initNewGame()
    }

    fun nextLevel(){
        initNewGame()
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

    fun moveEnemies(pixels: Int){
        if (direction != Direction.none){
            for (e in enemies){
                //is enemy left or right of pacman
                if (e.x > pacman.x){
                    e.x = e.x - pixels
                }
                if (e.x < pacman.x){
                    //move right
                    e.x = e.x + pixels
                }
                //is enemy up or down of pacman
                if (e.y > pacman.y){
                    //move up
                    e.y = e.y - pixels
                }
                if (e.y < pacman.y){
                    //move down
                    e.y = e.y + pixels
                }

            }
        }

        Log.d("Pacman", "X: ${pacman.x} Y: ${pacman.y}")
    }

    fun doCollisionCheck() {

        val remainingCoins = coins.filter { !it.taken }

        //check for collision on coins
        for (coin in remainingCoins){
            if (pacman.IsCollided(coin)){
                val indexOf = coins.indexOf(coin)
                Log.d("IndexOf", indexOf.toString())
                coin.taken = true
                points++
                onPoint?.let { it(points) }
            }
        }

        //check for collision on enemies
        for (enemy in enemies){
            if (pacman.IsCollided(enemy)){
                enemy.taken = true
                direction = Direction.none
                onGameRunning?.let { it(false) }
                Toast.makeText(context, "Game Over. Player Lost", Toast.LENGTH_SHORT).show()
            }
        }

        //game over
        if (remainingCoins.count() == 0 && coinsInitialized)
        {
            direction = Direction.none
            onGameRunning?.let { it(false) }
            //nexy Level please
            onChangeLevel?.let { it(level++) }
            Toast.makeText(context, "Game Over. Player Won", Toast.LENGTH_SHORT).show()
        }
    }
}

/*    //is the game won???
    var nextLevel: Boolean by Delegates.observable(false) {_, oldVal, newVal ->

    }

    var GameOver: Boolean by Delegates.observable(false){_ , oldVal, newVal ->
        if (newVal){
            //onGameOver?.invoke(true, "Player Lost the game")
        }
    }*/