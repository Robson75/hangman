package com.samdide.android.hangman

import android.util.Log

private const val TAG = "Hangman"

class Hangman(var word: String = "test") {

    public fun guess(guess_char: Char): String {
        val replaceCharacter: Char = '_'
        word = word.replace(guess_char, replaceCharacter, true)
        Log.d(TAG, "word after guess: " + word)
        return word
    }
}