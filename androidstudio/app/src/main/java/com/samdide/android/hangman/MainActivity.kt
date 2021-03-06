package com.samdide.android.hangman

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import kotlin.math.roundToInt


private const val TAG = "MainActivity"

// original images aspect ratio = 2.1/1.5
private const val IMAGE_ASPECT_RATIO = 2.1 / 1.5
private const val MAX_GUESSES = 7

class MainActivity : AppCompatActivity() {
    lateinit var imageHolder: ImageView
    lateinit var escapeAnimation: AnimationDrawable
    lateinit var word: String
    lateinit var hangman: Hangman
    lateinit var imageStar: ImageView


    var wordList = arrayOf("BANANA", "RAT", "GARLIC", "SPIDER", "tooth", "piano", "library",
        "finger", "dance", "car", "television", "umbrella", "tiger", "hospital", "car", "cradle",
        "important", "secret", "quiz", "parachute", "mother", "hotel", "restaurant", "mountain",
        "dryclean", "laugh", "cry", "tennis", "jazz", "funny", "children", "point", "egg")


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.info -> {
            Log.d(TAG, "info button clicked")
            val intent = Intent(this, Info::class.java)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation =  ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setContentView(R.layout.activity_main)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        imageStar = findViewById(R.id.star)
        imageStar.visibility = View.GONE
        imageHolder = findViewById(R.id.hangManView)
        imageHolder.layoutParams.width = width;
        imageHolder.layoutParams.height = (width * IMAGE_ASPECT_RATIO).roundToInt();
        initGame()
        //hangman.guess('l')
        //hangman.guess('h')
    }

    fun character_click(view: View) {
        val b: Button = view as Button
        val guess_char: Char = b.text.toString()[0]
        b.disable()

        var displayWord:String = hangman.guess(guess_char)
        setDisplayText(displayWord)
        var guesses:Int = hangman.getGuesses()
        setImage(guesses)

        // handle lose condition
        if (guesses >= MAX_GUESSES){
            toggleButtons(window.decorView, false)
            showCorrectWord()
        }

        // handle win condition
        Log.d(TAG, "display word: " + displayWord + " word: " + word)
        if (displayWord.replace(" ", "").equals(word)){
            runWinAnimation()
        }
    }

    private fun showCorrectWord(){
        val word_TV = findViewById<TextView>(R.id.word)
        Handler().postDelayed({
            word_TV.alpha = 0f
            word_TV.text = word
            word_TV.animate()
                .alpha(1f)
                .setDuration(1000)

        }, 1000)

    }



    public fun setDisplayText(word: String){
        val word_TV = findViewById<TextView>(R.id.word)
        word_TV.text = word
    }

    public fun setImage(guesses: Int){

       when(guesses) {
            0 -> imageHolder.setBackgroundResource(R.drawable.hangman_base_1)
            1 -> imageHolder.setBackgroundResource(R.drawable.hangman_base_2)
            2 -> imageHolder.setBackgroundResource(R.drawable.hangman_base_3)
            3 -> imageHolder.setBackgroundResource(R.drawable.hangman_base_4)
            4 -> imageHolder.setBackgroundResource(R.drawable.hangman_base_5)
            5 -> imageHolder.setBackgroundResource(R.drawable.hangman_base_6)
            6 -> imageHolder.setBackgroundResource(R.drawable.hangman_base_7)
            7 -> run_escape_animation()
            else -> println("Number too high")
        }

    }
    private fun run_escape_animation(){
        imageHolder.setBackgroundResource(R.drawable.hangman_base_8)
        Handler().postDelayed({
            imageHolder.setBackgroundResource(R.drawable.escape)
            escapeAnimation = imageHolder.background as AnimationDrawable
            escapeAnimation.start()
        }, 500)
        Handler().postDelayed({
           initGame()
        }, 3500)

    }
    private fun initGame(){
        word = getNewWord()
        hangman = Hangman(word)
        // set hangman graphic to first frame
        setImage(0)
        toggleButtons(window.decorView, true)
        val word_TV = findViewById<TextView>(R.id.word)
        word_TV.text = hangman.get_display_word()
        word_TV.alpha = 0f
        imageHolder.animate()
            .alpha(1f)
            .setDuration(1000)

        word_TV.animate()
            .alpha(1f)
            .setDuration(1000)


    }
    private fun runWinAnimation(){
        Log.d(TAG, "run win animation")
        imageHolder.animate()
            .alpha(0f)
            .setDuration(200)

        imageStar.apply {
            // Set the star view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE
            scaleX = 0.2f
            scaleY = 0.2f

            // Animate the star view to 100% opacity, and clear any animation
            // listener set on the view.
            animate()
                .alpha(1f)

                .scaleX(3f)
                .scaleY(3f)
                .setDuration(400)
                .setListener(null)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        Handler().postDelayed({
                            fadeOut()
                        }, 500)

                    }
                })
        }

    }
    private fun fadeOut(){
        // set hangman graphic to first frame
        setImage(0)

        val word_TV = findViewById<TextView>(R.id.word)
        imageStar.animate()
            .alpha(0f)
            .setDuration(500)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    initGame()
                }
            })
        word_TV.animate()
            .alpha(0f)
            .setDuration(500)

    }

    private fun getNewWord():String{
        var maxNr = wordList.size - 1
        var wordIndex = (0..maxNr).random()
        var newWord = wordList[wordIndex].toUpperCase()
        return newWord
    }

    private fun toggleButtons(v: View, enable: Boolean) {
        val viewgroup = v as ViewGroup
        for (i in 0 until viewgroup.childCount) {
            val v1 = viewgroup.getChildAt(i)
            (v1 as? ViewGroup)?.let { toggleButtons(it, enable) }
            if (v1 is Button ) {
                if(enable) {
                    v1.enable()
                }else{
                    v1.disable()
                }
            }
        }
    }
    private fun View.disable() {
        alpha = 0.2f
        isClickable = false
    }

    private fun View.enable(){
        alpha = 1f
        isClickable = true
    }

}
