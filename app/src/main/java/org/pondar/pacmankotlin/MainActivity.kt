package org.pondar.pacmankotlin

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    //reference to the game class.
    private var game: Game? = null

    private var timer: Timer = Timer()
    private var counter : Int = 0
    private var running = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        game = Game(this)

        game?.setGameView(gameView)
        gameView.setGame(game)
        game?.newGame()

        //callback functions
        game?.onGameRunning = {isRunning  ->
            running = isRunning
        }

        game?.onPoint = {points ->
            this.pointsView.text = "${getString(R.string.points)} $points"
        }

        game?.onChangeLevel = {level ->
            //add coins + enemies
            game?.numberOfEnemies = Random.nextInt(0,3)
            game?.numberOfCoins = game?.numberOfCoins!! + 1
            game?.nextLevel()
            this.levelView.text = "${getString(R.string.level)} $level"
        }

        //init all buttons
        for (i in 0 until buttons.childCount){
            val button = buttons.getChildAt(i)
            if (button is Button){
                Log.d("ButtonId", resources.getResourceEntryName(button.id))
                button.setOnClickListener {
                    game?.changeDirection(resources.getResourceEntryName(button.id))
                }
            }
        }

        //running = true
        //set timer
        timer.schedule(object: TimerTask(){
            override fun run() {
                timerMethod()
            }
        }, 0 , 30)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_newGame) {
            Toast.makeText(this, "New Game clicked", Toast.LENGTH_LONG).show()
            game?.newGame()
            return true
        } else if (id == R.id.action_pause){
            item.setTitle(resources.getString(R.string.pause))
            if (running){
                item.setTitle(resources.getString(R.string.unpause))
            }
            running = !running
            return  true
        } else if (id == R.id.action_share){
            startActivity(Intent.createChooser(share(pointsView.text.toString()), null))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun timerMethod(){
        this.runOnUiThread(timerTick)
    }
    private val timerTick = Runnable {
        if (running)
        {
            counter++
            game?.movePacman(8)
            game?.moveEnemies(4)
        }
    }

    private fun share(point: String) = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_SUBJECT, "PacmanKotlin awesome highscore")
        putExtra(Intent.EXTRA_TEXT, "I got this amazing number of ${point}. I'd bet You can't beat that.")
        type = "text/plain"
    }
}
