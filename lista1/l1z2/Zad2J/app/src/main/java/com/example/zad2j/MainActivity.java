package com.example.zad2j;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

        public int r1=0;
        public int r2=0;
        public int number=0;
        public int n = 100;
        Random rand = new Random();
        @Override
        public void onCreate(Bundle savedInstanceState) {

                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                number = rand.nextInt(n);
                TextView RightB = (TextView) findViewById(R.id.RightButton);
                RightB.setText("It's smaller");
                TextView LeftB = (TextView) findViewById(R.id.LeftButton);
                LeftB.setText("It's higher");
                roll();
        }

        public void roll()
        {
            r1 = rand.nextInt(n-r2) + r2;
            TextView Place4number = (TextView) findViewById(R.id.Place4number);
            Place4number.setText("" + r1);

        }
        public void ButtonLeftClick(View view) {
                if(r1<=number) {
                        r2 = r1;
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
                roll();
        }
        public void ButtonRightClick(View view) {
                if(r1>=number)
                {
                        n=r1;
                        if (r1 == number)
                        {
                                Toast.makeText(this, "Wygrales", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                                Toast.makeText(this, "Dobrze", Toast.LENGTH_SHORT).show();
                        }
                }
                else
                {

                Toast.makeText(this, "Zle", Toast.LENGTH_SHORT).show();
                }
                roll();
        }
}