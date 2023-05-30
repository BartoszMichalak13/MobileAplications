package com.example.kolkoikrzyzyk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update



class GameViewModel : ViewModel() {

    //var state by mutableStateOf(GameState())


    private val _gameState = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _gameState.asStateFlow()

    val boardItems: MutableMap<Int, BoardCellValue> = mutableMapOf()

    fun setgameSize(gameSize: String?) {
        println("gs $gameSize")
        val gsInt: Int = gameSize?.toInt()!!

        _gameState.value.gameSize.value = gsInt

        for (i in 1..(_gameState.value.gameSize.value)*(_gameState.value.gameSize.value)){
            boardItems[i] = BoardCellValue.NONE
        }
        println("gs after ${_gameState.value.gameSize.value}")

        println("state after ${state.value.gameSize.value}")

    }


    fun onAction(action: UserAction) {
        when (action) {
            is UserAction.BoardTapped -> {
                addValueToBoard(action.cellNo)
            }
            UserAction.PlayAgainButtonClicked -> {
                gameReset()
            }
            else -> {}
        }
    }

    private fun gameReset() {
        boardItems.forEach { (i, _) ->
            boardItems[i] = BoardCellValue.NONE
        }
        _gameState.value.hintText.value = "Player 'O' turn"
        _gameState.value.currentTurn.value = BoardCellValue.CIRLCE
        _gameState.value.victoryType.value = VictoryType.NONE
        _gameState.value.hasWon.value = false
    }

    private fun addValueToBoard(cellNo: Int) {
        if (boardItems[cellNo] != BoardCellValue.NONE) {
            return
        }
        if (_gameState.value.currentTurn.value == BoardCellValue.CIRLCE) {
            boardItems[cellNo] = BoardCellValue.CIRLCE
            if (checkForVictory(BoardCellValue.CIRLCE)) {
                _gameState.value.hintText.value = "Player 'O' Won"
                _gameState.value.currentTurn.value = BoardCellValue.NONE
                _gameState.value.playerCircleCount.value = _gameState.value.playerCircleCount.value + 1
                _gameState.value.hasWon.value = true
            } else if (hasBoardFull()) {
                _gameState.value.hintText.value = "Game Draw"
                _gameState.value.drawCount.value = _gameState.value.drawCount.value + 1
            } else {
                _gameState.value.hintText.value = "Player 'X' turn"
                _gameState.value.currentTurn.value = BoardCellValue.CROSS
            }
        } else if (_gameState.value.currentTurn.value == BoardCellValue.CROSS) {
            boardItems[cellNo] = BoardCellValue.CROSS
            if (checkForVictory(BoardCellValue.CROSS)) {
                _gameState.value.hintText.value = "Player 'X' Won"
                _gameState.value.currentTurn.value = BoardCellValue.NONE
                _gameState.value.playerCrossCount.value = _gameState.value.playerCrossCount.value + 1
                _gameState.value.hasWon.value = true
            } else if (hasBoardFull()) {
                _gameState.value.hintText.value = "Game Draw"
                _gameState.value.drawCount.value = _gameState.value.drawCount.value + 1
            } else {
                    _gameState.value.hintText.value = "Player 'O' turn"
                    _gameState.value.currentTurn.value = BoardCellValue.CIRLCE
            }
        }
    }
    private fun checkForVictory(boardValue: BoardCellValue): Boolean {
        boardItems.forEach { (i, boardCellValue) ->
            if (i % _gameState.value.gameSize.value <= _gameState.value.gameSize.value - 2 && i % _gameState.value.gameSize.value != 0) {
                if (boardItems[i] == boardValue && boardItems[i + 1] == boardValue && boardItems[i + 2] == boardValue) {
                    _gameState.value.victoryType.value = VictoryType.VERTICAL1
                    return true
                }
            }
            if (i + (2 * _gameState.value.gameSize.value) <= (_gameState.value.gameSize.value) * (_gameState.value.gameSize.value)) {
                if (boardItems[i] == boardValue && boardItems[i + _gameState.value.gameSize.value] == boardValue && boardItems[i + (2 * _gameState.value.gameSize.value)] == boardValue) {
                    _gameState.value.victoryType.value = VictoryType.HORIZONTAL1
                    return true
                }
            }
            //naprawic te 2 warunki + zobaczyc czy dziala i rysowanie (jakos) wygranej
            if (i % _gameState.value.gameSize.value <= _gameState.value.gameSize.value - 2 && i % _gameState.value.gameSize.value != 0) {
                if (i + (2 * _gameState.value.gameSize.value) + 2 <= (_gameState.value.gameSize.value) * (_gameState.value.gameSize.value)) {
                    if (boardItems[i] == boardValue && boardItems[i + _gameState.value.gameSize.value + 1] == boardValue && boardItems[i + (2 * _gameState.value.gameSize.value) + 2] == boardValue) {
                        _gameState.value.victoryType.value = VictoryType.DIAGONAL1
                        return true
                    }
                }
            }
            if (i % _gameState.value.gameSize.value >= 3 || i % _gameState.value.gameSize.value == 0) {
                if (i + (2 * _gameState.value.gameSize.value) - 2 <= (_gameState.value.gameSize.value) * (_gameState.value.gameSize.value)) {
                    if (boardItems[i] == boardValue && boardItems[i + _gameState.value.gameSize.value - 1] == boardValue && boardItems[i + (2 * _gameState.value.gameSize.value) - 2] == boardValue) {
                        _gameState.value.victoryType.value = VictoryType.DIAGONAL2
                        return true
                    }
                }

            }
        }
        return false

    }

    private fun hasBoardFull(): Boolean {
        if (boardItems.containsValue(BoardCellValue.NONE)) return false
        return true
    }
}