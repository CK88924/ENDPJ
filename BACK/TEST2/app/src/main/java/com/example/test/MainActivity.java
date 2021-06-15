package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button chart,captcha,AES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindId();
    }//onCreate()

    private void  FindId(){
        chart = (Button) findViewById(R.id.chart);
        captcha =(Button) findViewById(R.id.captcha);
        AES =(Button) findViewById(R.id.AES);
        chart.setOnClickListener(btnListener);
        captcha.setOnClickListener(btnListener);
        AES.setOnClickListener(btnListener);

    }//FindId()

    private View.OnClickListener btnListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (v.getId()==R.id.chart){
                Intent intent = new Intent(MainActivity.this, CHART.class);
                startActivity(intent);

            }//chart()
            if (v.getId()==R.id.captcha){
                Intent intent = new Intent(MainActivity.this, CAPTCHA.class);
                startActivity(intent);

            }//captcha
            if (v.getId()==R.id.AES){
                Intent intent = new Intent(MainActivity.this, PASSWORD.class);
                startActivity(intent);

            }//AES()

        }//onClick()

    };//btnListener()

}//main()