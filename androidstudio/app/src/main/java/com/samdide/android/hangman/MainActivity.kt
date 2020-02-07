package com.samdide.android.hangman

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.*
import kotlin.math.roundToInt


private const val TAG = "MainActivity"

// original images aspect ratio = 2.1/1.5
private const val IMAGE_ASPECT_RATIO = 2.1 / 1.5

class MainActivity : AppCompatActivity() {
    lateinit var imageHolder: ImageView
    lateinit var escapeAnimation: AnimationDrawable

    // test with word "hello"
    var word = "HELLO"
    var hangman = Hangman(word)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        imageHolder = findViewById(R.id.hangManView)
        imageHolder.layoutParams.width = width;
        imageHolder.layoutParams.height = (width * IMAGE_ASPECT_RATIO).roundToInt();
        imageHolder.setBackgroundResource(R.drawable.escape)


        val word_TV = findViewById<TextView>(R.id.word)
        word_TV.text = hangman.get_display_word()
        setImage(0)
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
    }

    private fun View.disable() {
        alpha = 0.2f
        isClickable = false
    }

    fun setDisplayText(word: String){
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
        imageHolder.setBackgroundResource(R.drawable.escape)
        escapeAnimation = imageHolder.background as AnimationDrawable

        escapeAnimation.start()
    }
}
