package com.example.zadanko1

class Cell(value: Int) {
    val BOMB = -1
    val BLANK = 0

    private var value = 0
    private var isRevealed = false
    private var isFlagged = false

    init {
        this.value = value
    }

    fun getValue(): Int {
        return value
    }

    fun isRevealed(): Boolean {
        return isRevealed
    }

    fun setRevealed(revealed: Boolean) {
        isRevealed = revealed
    }

    fun isFlagged(): Boolean {
        return isFlagged
    }

    fun setFlagged(flagged: Boolean) {
        isFlagged = flagged
    }
}