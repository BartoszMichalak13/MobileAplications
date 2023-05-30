package com.example.zad2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    public var r1=0;
    public var r2=0;
    public var number=0;
    public var n = 100;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        number = Random.nextInt(n)
        findViewById<TextView>(R.id.RightButton).text = "It's smaller"
        findViewById<TextView>(R.id.LeftButton).text = "It's higher"
        roll()
    }

    fun roll()
    {
        r1 = Random.nextInt(n-r2) + r2
        findViewById<TextView>(R.id.Place4number).text = "" + r1
    }
    fun ButtonLeftClick(view: View) {
        if(r1<=number) {
            r2 = r1
            if (r1 == number) {
                Toast.makeText(this, "Wygrales", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Dobrze", Toast.LENGTH_SHORT).show();

            }
        }
        else
        {
            Toast.makeText(this, "Zle", Toast.LENGTH_SHORT).show();
        }
        roll()
    }
    fun ButtonRightClick(view: View) {
        if(r1>=number)
        {
            n=r1
            if (r1 == number) {
                Toast.makeText(this, "Wygrales", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Dobrze", Toast.LENGTH_SHORT).show();

            }
        }
        else
        {

            Toast.makeText(this, "Zle", Toast.LENGTH_SHORT).show();
        }
        roll()
    }
}