package com.example.zadanko1

import kotlin.random.Random

class MineGrid(size: Int) {
    //val cellsRW: MutableList<Cell> = mutableListOf()        // 1
    val cellsR: ArrayList<Cell> = arrayListOf()
    var size = 0
    init{
        this.size = size
        for(i in 1..size*size) {
            cellsR.add(Cell(0))//0==BLANK
        }
    }

    fun generateGrid(numberBombs: Int): Unit{
        var bombsPlaced: Int = 0
        while (bombsPlaced < numberBombs){
            var x: Int = Random.nextInt(0,size)
            var y: Int = Random.nextInt(0,size)

            var index = toIndex(x,y)
            if(cellsR.get(index).getValue() == 0){//BLANK
                println("bmb created")
                cellsR.set(index, Cell(-1))//BOMB
                bombsPlaced++
            }
        }
        for (x in 0 until size) {
            for (y in 0 until size) {
                if (cellAt(x, y)!!.getValue() != (-1)) {
                    var adjacentCells = adjacentCells(x, y)
                    var countBombs = 0
                    for (cell:Cell in adjacentCells) {
                        if (cell.getValue() == (-1)) {
                            println("do we have bomb?")
                            countBombs++
                        }
                    }
                    if (countBombs > 0) {
                        cellsR.set(x + (y * size), Cell(countBombs))
                    }
                }
            }
        }
    }
    fun adjacentCells(x:Int, y:Int): ArrayList<Cell>{
        var adjacentcells: ArrayList<Cell> = arrayListOf()

        var cellList: ArrayList<Cell?> = arrayListOf()
        cellList.add(cellAt(x-1,y))
        cellList.add(cellAt(x+1,y))
        cellList.add(cellAt(x-1,y-1))
        cellList.add(cellAt(x,y-1))
        cellList.add(cellAt(x+1,y-1))
        cellList.add(cellAt(x-1,y+1))
        cellList.add(cellAt(x,y+1))
        cellList.add(cellAt(x+1,y+1))
        //var cellsList: ArrayList<Cell?> = cellListRW

        for (cell in cellList){
            if(cell != null){
                adjacentcells.add(cell)
                println("cell $cell")
            }
        }
       // var adjacentcells: ArrayList<Cell> = adjacentcellsRW
        println("adjacent $adjacentcells")
        println("celllist $cellList")
        return adjacentcells
    }

    fun revealAllBombs():Unit{
        for (cell: Cell in cellsR){
            if(cell.getValue() == (-1)){
                cell.setRevealed(true)
            }
        }
    }
    fun toIndex(x: Int, y: Int): Int{
        return  (x + (y*size))
    }
    fun toXY(index: Int): IntArray{
        val y: Int = index / size
        val x = index - (y*size)
        return intArrayOf(x,y)
    }

    fun cellAt(x:Int, y:Int): Cell? {
        if(x < 0 || x >= size || y < 0 || y >= size){
            println("null cellat klik")
            return null
        }
        println("cellat klik")

        return cellsR.get(toIndex(x,y))
    }
    fun getCells(): ArrayList<Cell> {
        return cellsR
    }
}