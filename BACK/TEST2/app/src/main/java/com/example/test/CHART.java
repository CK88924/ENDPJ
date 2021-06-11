package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class CHART extends AppCompatActivity {
    private BarChart barChart;
    private BarData barData;
    private BarDataSet barDataSet;
    private ArrayList barEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_h_a_r_t);
        init_bar_chart();
    }//onCreate()

    private  void  init_bar_chart(){
        barChart= findViewById(R.id.barChart);
        barChart.setBackgroundColor(Color.WHITE);
        //不显示图表网格
        barChart.setDrawGridBackground(false);
        //背景阴影
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);
        //显示边框
        barChart.setDrawBorders(true);
        //设置动画效果
        getEntries();
        barDataSet = new BarDataSet(barEntries, "顏色指標");
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(25f);

    }//init_bar_chart()

    private void getEntries() {
        barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1f, 2));
        barEntries.add(new BarEntry(2f, 4));
        barEntries.add(new BarEntry(4f, 6));
        barEntries.add(new BarEntry(5f, 8));
        barEntries.add(new BarEntry(6f, 10));
        barEntries.add(new BarEntry(3f, 2));
        barEntries.add(new BarEntry(7f, 15));
    }//getEntries()
}//main()