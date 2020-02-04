package com.samdide.android.hangman

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt


private const val TAG = "MainActivity"

// original images aspect ratio = 2.1/1.5
private const val IMAGE_ASPECT_RATIO = 2.1 / 1.5

class MainActivity : AppCompatActivity() {
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
        val escapeImage = findViewById<ImageView>(R.id.hangManView)
        escapeImage.layoutParams.width = width;
        escapeImage.layoutParams.height = (width * IMAGE_ASPECT_RATIO).roundToInt();
        escapeImage.setBackgroundResource(R.drawable.escape)
        escapeAnimation = escapeImage.background as AnimationDrawable

        escapeImage.setOnClickListener{
            escapeAnimation.start() }

        val word_TV = findViewById<TextView>(R.id.word)
        word_TV.text = hangman.get_display_word()
        //hangman.guess('l')
        //hangman.guess('h')
    }

    fun character_click(view: View) {
        val b: Button = view as Button
        val guess_char: Char = b.text.toString()[0]

        var displayWord:String = hangman.guess(guess_char)
        setDisplayText(displayWord)
    }

    fun setDisplayText(word: String){
        val word_TV = findViewById<TextView>(R.id.word)
        word_TV.text = word
    }

}
