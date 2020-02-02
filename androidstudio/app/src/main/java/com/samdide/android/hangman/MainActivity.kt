package com.samdide.android.hangman

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ImageView
import kotlin.math.roundToInt

// original images aspect ratio = 2.1/1.5
private const val IMAGE_ASPECT_RATIO = 2.1 / 1.5

class MainActivity : AppCompatActivity() {
    lateinit var escapeAnimation: AnimationDrawable

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
    }
}
