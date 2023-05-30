package com.example.zadanko1

class MinesweeperGame(size: Int,numberOfBombs: Int) {
    private var mineGrid = MineGrid(size)
    private var clearMode: Boolean = true
    private var flagMode: Boolean =false
    private var isGameOver: Boolean = false
    private var timeExpired:Boolean = false
    private var flagCount: Int = 0
    private var numberOfBombs:Int = numberOfBombs

    init{
        mineGrid.generateGrid(numberOfBombs)

    }
    fun handleCellClick(cell:Cell):Unit{
        if (!isGameOver && !isGameWon() && !timeExpired) {
            if (clearMode) {
                clear(cell)
            } else if (flagMode){
                flag(cell)
            }
        }
    }
    fun clear(cell:Cell){
        var index = getMineGrid().getCells().indexOf(cell)
        getMineGrid().getCells().get(index).setRevealed(true)

        if (cell.getValue() == (0)) {
            val toClear: MutableList<Cell> = ArrayList()
            val toCheckAdjacents: MutableList<Cell> = ArrayList()
            toCheckAdjacents.add(cell)

            while (toCheckAdjacents.size > 0) {
                val c = toCheckAdjacents[0]
                val cellIndex = getMineGrid().getCells().indexOf(c)
                val cellPos = getMineGrid().toXY(cellIndex)
                for (adjacent in getMineGrid().adjacentCells(cellPos[0], cellPos[1])) {
                    if (adjacent.getValue() == (0)) {
                        if (!toClear.contains(adjacent)) {
                            if (!toCheckAdjacents.contains(adjacent)) {
                                toCheckAdjacents.add(adjacent)
                            }
                        }
                    } else {
                        if (!toClear.contains(adjacent)) {
                            toClear.add(adjacent)
                        }
                    }
                }
                toCheckAdjacents.remove(c)
                toClear.add(c)
            }
            for (c in toClear) {
                c.setRevealed(true)
            }
        } else if (cell.getValue() == (-1)){
            isGameOver = true
        }
    }
    fun isGameWon(): Boolean {
        var numbersUnrevealed = 0
        for (c in getMineGrid().getCells()) {
            if (c.getValue() != (-1) && c.getValue() != (0) && !c.isRevealed()) {
                numbersUnrevealed++
            }
        }
        return if (numbersUnrevealed == 0) {
            true
        } else {
            false
        }
    }
    fun toggleMode():Unit{
        clearMode = !clearMode
        flagMode = !flagMode
    }

    fun flag(cell:Cell):Unit{
        if(!cell.isRevealed()) {
            cell.setFlagged(!cell.isFlagged())
            var count: Int = 0
            for (c in getMineGrid().getCells()) {
                if (c.isFlagged()) {
                    count++
                }
            }
            flagCount = count
        }
    }

    fun outOfTime():Unit{
        timeExpired = true
    }
    fun getMineGrid(): MineGrid{
        return mineGrid
    }
    fun isGameOver():Boolean{
        return isGameOver
    }
    fun isFlagMode():Boolean{
        return flagMode
    }
    fun getFlagCount():Int{
        return flagCount
    }
    fun getNumberOfBombs():Int{
        return numberOfBombs
    }
}