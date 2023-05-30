package com.example.zadanko1

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), OnCellClickListener{
    lateinit var gridRecyclerView : RecyclerView
    lateinit var mineGridRecyclerAdapter : MineGridRecyclerAdapter
    lateinit var game : MinesweeperGame
    lateinit var smiley: TextView
    lateinit var timer: TextView
    lateinit var flag: TextView
    lateinit var flagsCount: TextView
    lateinit var countDownTimer: CountDownTimer
    var secondsElapsed: Int = 0
    var timerStarted: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flag = findViewById(R.id.activity_main_flag)
        flagsCount = findViewById(R.id.activity_main_flagsleft)
        flag.setOnClickListener {
            game.toggleMode()
            if (game.isFlagMode()) {
                val border: GradientDrawable = GradientDrawable()
                border.setColor(-0x1)
                border.setStroke(1, -0x1000000)
                flag.background = border
            } else {
                val border = GradientDrawable()
                border.setColor(-0x1)
                flag.background = border
            }
        }

        smiley = findViewById(R.id.activity_main_smiley)
        smiley.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                println("Smiley klik")
                game = MinesweeperGame(9,9)
                mineGridRecyclerAdapter.setCells(game.getMineGrid().getCells())
                timerStarted = false
                countDownTimer.cancel()
                secondsElapsed = 0
                timer.setText(R.string.default_count)
                flagsCount.setText(String.format("%03d", game.getNumberOfBombs() - game.getFlagCount()))

            }
        })

        timer = findViewById(R.id.activity_main_timer)
        timerStarted = false
        countDownTimer = object : CountDownTimer(999000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                secondsElapsed += 1
                timer.text = kotlin.String.format("%03d", secondsElapsed)
            }

            override fun onFinish() {
                game.outOfTime()
                Toast.makeText(applicationContext, "Game Over: Timer Expired", Toast.LENGTH_SHORT)
                    .show()
                game.getMineGrid().revealAllBombs()
                mineGridRecyclerAdapter.setCells(game.getMineGrid().getCells())
            }
        }
        gridRecyclerView = findViewById(R.id.activity_main_grid)
        gridRecyclerView.layoutManager = GridLayoutManager(this, 9)
        game = MinesweeperGame(9, 9)
        mineGridRecyclerAdapter = MineGridRecyclerAdapter(game.getMineGrid().getCells(),this)
        gridRecyclerView.adapter = mineGridRecyclerAdapter
        flagsCount.setText(String.format("%03d", game.getNumberOfBombs() - game.getFlagCount()))
    }

    override fun onCellClick(cell: Cell) {
        game.handleCellClick(cell)

        flagsCount.setText(String.format("%03d", game.getNumberOfBombs() - game.getFlagCount()))
        if(!timerStarted){
            countDownTimer.start()
            timerStarted = true
        }
        if(game.isGameOver()){
            Toast.makeText(applicationContext, "Game is Over", Toast.LENGTH_SHORT).show()
            game.getMineGrid().revealAllBombs()
        }
        if (game.isGameWon()) {
            //countDownTimer.cancel();
            Toast.makeText(applicationContext, "Game Won", Toast.LENGTH_SHORT).show();
            game.getMineGrid().revealAllBombs();
        }
        mineGridRecyclerAdapter.setCells(game.getMineGrid().getCells())

    }
}