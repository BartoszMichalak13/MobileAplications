package com.example.wisielec

import android.content.Context
import kotlin.random.Random

class GameManager(val context: Context) {

    private var lettersUsed: String = ""
    private lateinit var underscoreWord: String
    private lateinit var wordToGuess: String
    private val maxTries = 9
    private var currentTries = 0
    private var drawable: Int = R.drawable.wisielec1

    fun startNewGame(): GameState {
        lettersUsed = ""
        currentTries = 0
        drawable = R.drawable.wisielec10
        val wordsarray = context.resources.getStringArray(R.array.words)
        val randomIndex = Random.nextInt(0, wordsarray.size)
        wordToGuess = wordsarray[randomIndex]
        generateUnderscores(wordToGuess)
        return getGameState()
    }

    fun generateUnderscores(word: String) {
        val sb = StringBuilder()
        word.forEach { char ->
            if (char == '/') {
                sb.append('/')
            } else {
                sb.append("_")
            }
        }
        underscoreWord = sb.toString()
    }

    fun play(letter: Char): GameState {
        if (lettersUsed.contains(letter)) {
            return GameState.Running(lettersUsed, underscoreWord, drawable)
        }

        lettersUsed += letter
        val indexes = mutableListOf<Int>()

        wordToGuess.forEachIndexed { index, char ->
            if (char.equals(letter, true)) {
                indexes.add(index)
            }
        }

        var finalUnderscoreWord = "" + underscoreWord // _ _ _ _ _ _ _ -> E _ _ _ _ _ _
        indexes.forEach { index ->
            val sb = StringBuilder(finalUnderscoreWord).also { it.setCharAt(index, letter) }
            finalUnderscoreWord = sb.toString()
        }

        if (indexes.isEmpty()) {
            currentTries++
        }

        underscoreWord = finalUnderscoreWord
        return getGameState()
    }

    private fun getHangmanDrawable(): Int {
        return when (currentTries) {
            0 -> R.drawable.wisielec1
            1 -> R.drawable.wisielec2
            2 -> R.drawable.wisielec3
            3 -> R.drawable.wisielec4
            4 -> R.drawable.wisielec5
            5 -> R.drawable.wisielec6
            6 -> R.drawable.wisielec6_5
            7 -> R.drawable.wisielec7
            8 -> R.drawable.wisielec8
            9 -> R.drawable.wisielec10
            else -> R.drawable.wisielec10
        }
    }

    private fun getGameState(): GameState {
        if (underscoreWord.equals(wordToGuess, true)) {
            return GameState.Won(wordToGuess)
        }

        if (currentTries == maxTries) {
            return GameState.Lost(wordToGuess)
        }

        drawable = getHangmanDrawable()
        return GameState.Running(lettersUsed, underscoreWord, drawable)
    }
}