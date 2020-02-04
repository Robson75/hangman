package com.samdide.android.hangman

import android.util.Log

private const val TAG = "Hangman"

class Hangman(var word: String = "TEST") {
    private val replaceCharacter = '_'
    private var display_word: String
    private var found_characters = mutableSetOf<Char>()
    init{
        display_word = init_display(word)
    }

    public fun guess(guess_char: Char): String {
        display_word = ""
        // If guessed character in word add it to the set of found characters
        if(guess_char in word){
            found_characters.add(guess_char)
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

}