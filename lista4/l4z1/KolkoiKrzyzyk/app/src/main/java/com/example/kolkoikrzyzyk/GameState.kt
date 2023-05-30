package com.example.kolkoikrzyzyk

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class GameState(
    val gameSize: MutableState<Int> = mutableStateOf(0),
    val playerCircleCount: MutableState<Int> = mutableStateOf(0),
    val playerCrossCount: MutableState<Int> = mutableStateOf(0),
    val drawCount: MutableState<Int> = mutableStateOf(0),
    val hintText: MutableState<String> = mutableStateOf("Player 'O' turn"),
    val currentTurn: MutableState<BoardCellValue> = mutableStateOf(BoardCellValue.CIRLCE),
    val victoryType: MutableState<VictoryType> = mutableStateOf(VictoryType.NONE),
    val hasWon: MutableState<Boolean> = mutableStateOf(false)
)

enum class BoardCellValue {
    CIRLCE,
    CROSS,
    NONE
}

enum class VictoryType {
    HORIZONTAL1,
    HORIZONTAL2,
    HORIZONTAL3,
    VERTICAL1,
    VERTICAL2,
    VERTICAL3,
    DIAGONAL1,
    DIAGONAL2,
    NONE
}