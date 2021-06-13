package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.InputStream;
import java.util.ArrayList;
import org.apache.http.util.EncodingUtils;

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
        String str = read("RES.txt");
        String[] tokens = str.split(" ");
        barEntries = new ArrayList<>();
        final ArrayList<String> xAxisLabel = new ArrayList<>();
        for(int i =0 ; i <tokens.length; i++){
            xAxisLabel.add("值"+i);//x軸標籤設定
            barEntries.add(new BarEntry(Float.valueOf(String.valueOf(i)),Float.valueOf(tokens[i])));
        }//for

            /*x軸設定*/
            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1);
            xAxis.setDrawGridLines(false);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
            barChart.invalidate();




    }//getEntries()

    private  String read(String filename) {

        String res = "";
        try {
            //得到資源中的asset資料流
            InputStream in = getResources().getAssets().open(filename);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            in.close();
            res = EncodingUtils.getString(buffer, "UTF-8");

        }//try
        catch (Exception e) {
            e.printStackTrace();

        }//catch
        return res;
    }//read()

/*
        barEntries.add(new BarEntry(1f, 2));
        barEntries.add(new BarEntry(2f, 4));
        barEntries.add(new BarEntry(4f, 6));
        barEntries.add(new BarEntry(5f, 8));
        barEntries.add(new BarEntry(6f, 10));
        barEntries.add(new BarEntry(3f, 2));
        barEntries.add(new BarEntry(7f, 15));
 */




}//main()