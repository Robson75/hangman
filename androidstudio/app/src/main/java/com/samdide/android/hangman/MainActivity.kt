package com.samdide.android.hangman

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt


private const val TAG = "MainActivity"

// original images aspect ratio = 2.1/1.5
private const val IMAGE_ASPECT_RATIO = 2.1 / 1.5

class MainActivity : AppCompatActivity() {
    lateinit var imageHolder: ImageView
    lateinit var escapeAnimation: AnimationDrawable
    lateinit var word: String
    lateinit var hangman: Hangman
    lateinit var imageStar: ImageView
    var wordNr = 0

    // All words in uppercase
    var wordList = arrayOf("BANANA", "RAT", "GARLIC", "SPIDER", "FRACTION", "MERCURY", "APOSTROPHE")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initGame(wordNr)
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

        //handle win condition
        Log.d(TAG, "display word: " + displayWord + " word: " + word)
        if (displayWord.replace(" ", "").equals(word)){
            runWinAnimation()
        }
    }

    private fun View.disable() {
        alpha = 0.2f
        isClickable = false
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
            wordNr ++
            if (wordNr >= wordList.size) wordNr = 0
            initGame(wordNr)
        }, 5000)

    }
    private fun initGame(wordNr: Int){
        word = wordList[wordNr].toUpperCase()
        hangman = Hangman(word)

        setContentView(R.layout.activity_main)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        imageStar = findViewById(R.id.star)
        imageStar.visibility = View.GONE
        imageHolder = findViewById(R.id.hangManView)
        imageHolder.layoutParams.width = width;
        imageHolder.layoutParams.height = (width * IMAGE_ASPECT_RATIO).roundToInt();


        val word_TV = findViewById<TextView>(R.id.word)
        word_TV.text = hangman.get_display_word()

        // set hangman graphic to first frame
        setImage(0)
    }
    private fun runWinAnimation(){
        Log.d(TAG, "run win animation")
        imageStar.apply {
            // Set the star view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the star view to 100% opacity, and clear any animation
            // listener set on the view.
            animate()
                .alpha(1f)
                .setDuration(500)
                .setListener(null)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        wordNr ++
                        if (wordNr >= wordList.size) wordNr = 0
                        initGame(wordNr)
                    }
                })
        }
        // Animate the hangman view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        hangManView.animate()
            .alpha(0f)
            .setDuration(500)
    }
    private fun fadeInNewGame(){
        imageStar.animate()
            .alpha(0f)
    }
}
