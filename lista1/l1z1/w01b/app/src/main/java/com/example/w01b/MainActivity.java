package com.example.w01b;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int r1;
    private int r2;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score = 0;
        roll();
    }

    private void roll() {
        TextView textView = (TextView) findViewById(R.id.Points);
        textView.setText("Punkciki: " + score);
        Random random = new Random();
        r1 = random.nextInt(10);
        r2 = random.nextInt(10);
        TextView leftClick = (TextView) findViewById(R.id.LeftClick);
        leftClick.setText("" + r1);
        TextView rightClick = (TextView) findViewById(R.id.RightClick);
        rightClick.setText("" + r2);
    }

    public void BuuttonLeft(View view) {
        if(r1 >= r2) {
            score++;
            Toast.makeText(this, "Dobrze", Toast.LENGTH_SHORT).show();
        }
        else {
            score--;
            Toast.makeText(this, "Źle", Toast.LENGTH_SHORT).show();
        }
        roll();
    }

    public void BuuttonRight(View view) {
        if(r1 <= r2) {
            score++;
            Toast.makeText(this, "Dobrze", Toast.LENGTH_SHORT).show();
        }
        else {
            score--;
            Toast.makeText(this, "Źle", Toast.LENGTH_SHORT).show();
        }
        roll();
    }
}