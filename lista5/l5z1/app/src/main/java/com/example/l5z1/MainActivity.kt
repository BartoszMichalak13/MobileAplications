package com.example.l5z1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.sql.SQLOutput

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var btnGoal = findViewById<Button>(R.id.btnGoal)
        var btnAdd = findViewById<Button>(R.id.btnAdd)
        var textview = findViewById<EditText>(R.id.editTextNumber)
        var myView = findViewById<MyView>(R.id.progress_circle)
        var mytext =  findViewById<TextView>(R.id.textView)
        var percentRotated: Int = 0
        println("does it work at all?")
        btnAdd.setOnClickListener{
            println("click move")
            Thread{
                runOnUiThread{
                    myView.move()
                    percentRotated++
                    mytext.text = "" + percentRotated + "%"
                }
                Thread.sleep(10)
            }.start()
            Toast.makeText(this, "RANGE = 1", Toast.LENGTH_SHORT).show()
        }

        btnGoal.setOnClickListener{
            if(textview.text.toString().toInt() > 0) {
                println("click rangge = " + textview.text.toString().toInt() / 100f)

                Thread {
                    for ( i in 1..(textview.text.toString()).toInt() ) {
                        runOnUiThread {
                            myView.move()
                            percentRotated++
                            mytext.text = "" + percentRotated + "%"

                        }
                        Thread.sleep(10)
                    }
                }.start()
                Toast.makeText(
                    this,
                    "RANGE = " + textview.text.toString().toInt() / 100f,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}