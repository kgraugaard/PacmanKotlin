package org.pondar.pacmankotlin

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View


//note we now create our own view class that extends the built-in View class
class GameView : View {

    private var game: Game? = null
    private var h: Int = 0
    private var w: Int = 0 //used for storing our height and width of the view

    fun setGame(game: Game?) {
        this.game = game
    }


    /* The next 3 constructors are needed for the Android view system,
	when we have a custom view.
	 */
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun dpToPx(dp: Int): Int {
        return  (dp.toFloat() * Resources.getSystem().displayMetrics.density).toInt()
    }

    //In the onDraw we put all our code that should be
    //drawn whenever we update the screen.
    override fun onDraw(canvas: Canvas) {
        //Here we get the height and weight
        h = canvas.height
        w = canvas.width

        //update the size for the canvas to the game.
        game?.setSize(h - dpToPx(40) , w)

        //are the coins initiazlied?
        if (!(game!!.coinsInitialized))
            game?.initializeGoldcoins()

        //are Enemies?
        if (!(game!!.enemiesInitialized))
            game?.initializeEnemies()

        //Making a new paint object
        val paint = Paint()
        canvas.drawColor(Color.WHITE) //clear entire canvas to white color

        //draw the cooins first
        for (coin in game!!.coins)
        {
            if (!coin.taken){
                canvas.drawBitmap(
                        coin.bitmap!!,
                        coin.x.toFloat(),
                        coin.y.toFloat(),
                        paint)

            }
        }

        //draw an enemy
        for (enemy in game!!.enemies)
        {
            canvas.drawBitmap(
                    enemy.bitmap!!,
                    enemy.x.toFloat(),
                    enemy.y.toFloat(),
                    paint)
        }

        //draw the pacman
        val pac = game!!.pacman
        canvas.drawBitmap(pac.bitmap!!, pac.x!!.toFloat(),
                pac.y!!.toFloat(), paint)

        game?.doCollisionCheck()
        super.onDraw(canvas)
    }

}
