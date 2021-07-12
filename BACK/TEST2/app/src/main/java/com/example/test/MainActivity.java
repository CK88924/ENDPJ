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
    private Button chart,captcha,AES,icon_btn,qr,phone_function,line_chart,db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindId();
    }//onCreate()

    private void  FindId(){
        chart = (Button) findViewById(R.id.chart);
        line_chart =(Button)findViewById(R.id.line_chart);
        captcha =(Button) findViewById(R.id.captcha);
        AES =(Button) findViewById(R.id.AES);
        icon_btn = (Button)findViewById(R.id.icon_btn);
        qr = (Button)findViewById(R.id.qr);
        db = (Button)findViewById(R.id.db);
        phone_function=(Button)findViewById(R.id.phone_function);
        chart.setOnClickListener(btnListener);
        line_chart.setOnClickListener(btnListener);
        captcha.setOnClickListener(btnListener);
        AES.setOnClickListener(btnListener);
        icon_btn.setOnClickListener(btnListener);
        qr.setOnClickListener(btnListener);
        phone_function.setOnClickListener(btnListener);
        db.setOnClickListener(btnListener);

    }//FindId()

    private View.OnClickListener btnListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (v.getId()==R.id.chart){
                Intent intent = new Intent(MainActivity.this, CHART.class);
                startActivity(intent);

            }//chart()
            if (v.getId()==R.id.line_chart){
                Intent intent = new Intent(MainActivity.this, Line_Chart.class);
                startActivity(intent);

            }//line_chart()
            if (v.getId()==R.id.captcha){
                Intent intent = new Intent(MainActivity.this, CAPTCHA.class);
                startActivity(intent);

            }//captcha
            if (v.getId()==R.id.AES){
                Intent intent = new Intent(MainActivity.this, PASSWORD.class);
                startActivity(intent);

            }//AES()
            if (v.getId()==R.id.icon_btn){
                Intent intent = new Intent(MainActivity.this, ICIONLIST.class);
                startActivity(intent);

            }//icon_btn()
            if (v.getId()==R.id.qr){
                Intent intent = new Intent(MainActivity.this, QR.class);
                startActivity(intent);

            }//QR()
            if (v.getId()==R.id.phone_function){
                Intent intent = new Intent(MainActivity.this, PHONE.class);
                startActivity(intent);

            }//phone_function
            if (v.getId()==R.id.db){
                Intent intent = new Intent(MainActivity.this, DB_TEST.class);
                startActivity(intent);

            }//db

        }//onClick()

    };//btnListener()

}//main()