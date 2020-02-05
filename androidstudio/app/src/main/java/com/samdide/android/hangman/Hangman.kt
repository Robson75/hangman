package com.samdide.android.hangman

import android.R
import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView


private const val TAG = "Hangman"

class Hangman(var word: String = "TEST") {
    private val replaceCharacter = '_'
    private var display_word: String
    private var found_characters = mutableSetOf<Char>()
    private var guesses = 0
    init{
        display_word = init_display(word)
    }

    public fun guess(guess_char: Char): String {
        display_word = ""
        // If guessed character in word add it to the set of found characters
        if(guess_char in word){
            found_characters.add(guess_char)
        }else{
            guesses ++
        }
        for(char in word){
            if (char in found_characters){
                display_word += char + " "
            }else{
                display_word += replaceCharacter + " "
            }

        }
        Log.d(TAG, "word after guess: " + display_word)
        return display_word
    }

    private fun init_display(word: String):String{
        display_word = ""
        for (char in word){
            display_word += "_ "
        }
        return display_word
    }

    public fun get_display_word():String{
        return display_word
    }

    public fun getGuesses():Int{
        return guesses
    }

}