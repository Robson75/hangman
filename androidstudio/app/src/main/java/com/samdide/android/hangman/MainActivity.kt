package com.samdide.android.hangman

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ImageView
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    lateinit var escapeAnimation: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels
        val escapeImage = findViewById<ImageView>(R.id.hangManView)
        escapeImage.layoutParams.height = (width * 2.1 / 1.5).roundToInt();
        escapeImage.layoutParams.width = width;
        escapeImage.setBackgroundResource(R.drawable.escape)
        escapeAnimation = escapeImage.background as AnimationDrawable

        escapeImage.setOnClickListener{

            escapeAnimation.start() }
    }
}
