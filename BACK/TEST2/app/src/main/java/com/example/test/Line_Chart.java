package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.common.collect.Lists;

import org.apache.http.util.EncodingUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Line_Chart extends AppCompatActivity {
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line__chart);
        Init_Line_Chart();
    }//onCreate()
    private void Init_Line_Chart(){
        lineChart= findViewById(R.id.lineChart);
         Data_From_External();
         //setData("折線1","折線2");
         setLegend();
         maxLine();
         XLine();
         YLine();
         //Delete_Line();
         lineChart.invalidate(); // refresh
    }//Init_Line_Chart()

    private void setLine(LineDataSet set) {
        //設定線條的顏色
        set.setColor(Color.RED);
        //虛線模式下繪製直線
        set.enableDashedLine(20f, 5f, 0f);
        //點選後高亮線的顯示顏色
        set.enableDashedHighlightLine(50f, 15f, 0f);

        //設定資料小圓點的顏色
        set.setCircleColor(Color.GREEN);
        //設定圓點是否有空心
        set.setDrawCircles(true);
        //設定線條的寬度，最大10f,最小0.2f
        set.setLineWidth(1f);
        //設定小圓點的半徑，最小1f，預設4f
        set.setCircleRadius(5f);
        //設定是否顯示小圓點
        set.setDrawCircles(true);
        //設定字型顏色
        set.setValueTextColor(Color.BLUE);
        //設定字型大小
        set.setValueTextSize(20f);
        //設定是否填充
        set.setDrawFilled(true);
        //設定繪製折線的動畫時間

    }//setLine()
    private void setLegend(){
        Legend legend=lineChart.getLegend();
        legend.setTextColor(Color.RED);
        legend.setTextSize(20f);
        //設定圖例垂直對齊
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        //設定圖例居中
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        //設定圖例方向
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //設定圖例是在圖內還是圖外繪製
        legend.setDrawInside(false);
        //圖例條目之間距離
        legend.setXEntrySpace(4f);
        //設定圖例可否換行
        legend.setWordWrapEnabled(true);
        //設定圖例現狀為線.預設為方形
        // legend.setForm(Legend.LegendForm.LINE);
        //是否隱藏圖例/true_否，false_是
        legend.setEnabled(true);
    }//setLegend()

    private void maxLine(){
        //設定限制線
        LimitLine l1=new LimitLine(60f,"限制線");
        l1.setLineWidth(4f);
        l1.setTextSize(20f);
        l1.setLineColor(Color.RED);
        //允許在虛線模式下繪製(線段長度，分隔長度，偏移量)
        l1.enableDashedLine(10f,10f,0f);
        //設定限制線標籤的位置
        l1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        l1.setTextSize(10f);
        //新增限制線
        lineChart.getAxisLeft().addLimitLine(l1);
        //是否隱藏限制線/true_否，false_是
        l1.setEnabled(true);
    }//maxLine()
    private void XLine(){
        //設定x軸網格線
        XAxis xAxis=lineChart.getXAxis();
        //以虛線模式畫網格線
        xAxis.enableGridDashedLine(10f,10f,0f);
        //設定x軸最大值
        xAxis.setAxisMaximum(200f);
        //設定x軸最小值
        xAxis.setAxisMinimum(0f);

        //撤銷設定的最大值，讓軸自動計算
        xAxis.resetAxisMaximum();
        //撤銷設定的最小值，讓軸自動計算
        xAxis.resetAxisMinimum();
//        //設定x軸標籤數，預設為6個
        xAxis.setLabelCount(10);
//        //設定x軸標籤數，若強制啟用true，可能導致軸上的數字不均勻
//        xAxis.setLabelCount(10,true);

        //設定x軸之間的最小間隔。用於在圖表放大後標籤不至於重合
        xAxis.setGranularity(1f);
        //設定x軸軸線的寬度
        xAxis.setAxisLineWidth(1f);
        //設定軸線的顏色
        xAxis.setAxisLineColor(Color.BLUE);
        //設定x軸顯示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }//XLine()
    private void YLine(){
        //y軸預設顯示兩個軸線，左右

        //獲取圖表左邊y軸
        YAxis left=lineChart.getAxisLeft();
        //是否繪製0所在的網格線/預設false繪製
        left.setDrawZeroLine(true);
        //將網格線設定為虛線模式
        left.enableGridDashedLine(10f,10f,0f);
        //獲取圖表右邊y軸
        YAxis right=lineChart.getAxisRight();
        //禁用圖表右邊y軸
        right.setEnabled(false);
    }//YLine()
    private void Delete_Line() {
        //關閉邊框矩形
        lineChart.setDrawBorders(false);
        //不繪製y軸左邊的線
        lineChart.getAxisLeft().setDrawAxisLine(false);
        //不繪製y軸右邊的線
//        lineChart.getAxisRight().setDrawAxisLine(false);
        //禁用圖表右邊y軸
        lineChart.getAxisRight().setEnabled(false);
        //禁用x軸
        lineChart.getXAxis().setEnabled(false);
        //隱藏圖表左邊y軸標籤
        lineChart.getAxisLeft().setDrawLabels(false);
        //關閉x軸網格線./即豎線
//        lineChart.getXAxis().setDrawGridLines(false);

        //隱藏圖表右下角顯示內容
        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);
    }// Delete_Line()
    private void setData(String name1,String name2) {
        Def_2_X();
        //這裡隨機數了兩個集合
        ArrayList <Entry> list=new ArrayList<>();
        ArrayList <Entry> list2=new ArrayList<>();
        for (int i = 0; i <7; i++) {
            list.add(new Entry(i, (float) (Math.random() * 80)));
        }
        for (int i = 0; i <7; i++) {
            list2.add(new Entry(i, (float) (Math.random() * 80)));
        }
        //這裡兩條線
        LineDataSet set1 = new LineDataSet(list, name1);
        LineDataSet set2 = new LineDataSet(list2, name2);
        setLine(set1);
        setLine(set2);
        //避免看不清，將折線2的顏色更改
        set2.setColor(Color.BLACK);
        //建立一個數據集
        ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        LineData data = new LineData(dataSets);
        //設定資料
        lineChart.setData(data);
    }//setData
    private  void  Def_X(){
        String[]x_axis={"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(x_axis));
    }// Def_X()
    private  void Def_2_X(){
        String[]x_axis={"週一","週二","週三","週四","週五","週六","週日"};
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(x_axis));
    }// Def_X()
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
    private  void Data_From_External(){
        Def_X();
        Random random = new Random();
        ArrayList <Entry> temp_list=new ArrayList<>();
        ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        String str = read("RES2.txt");
        String[] tokens = str.split(" |\n");
        Float[] change_float_array = convert_StringArray_to_FloatArray(tokens);
        List<Float> all = new ArrayList<>(Arrays.asList(change_float_array));
        List<List<Float>> subLists = Lists.partition(new ArrayList<>(all),12); // 第一個參數是要被分割的list，第二個參數是要分割的大小。

        for(int i  = 0; i < subLists.size(); i++){
            for(int j  = 0; j < subLists.get(i).size(); j++){
                temp_list.add(new Entry(j,subLists.get(i).get(j)));
            }//for(2)
        }//for(1)
        List<List<Entry>> test_subLists = Lists.partition(new ArrayList<>(temp_list),12);
        for(int i =0; i < test_subLists.size(); i++){
            LineDataSet temp_set = new LineDataSet(test_subLists.get(i),"折線" + i);
            temp_set.setDrawValues(false);//不顯示數值
            setLine(temp_set);
            Animation(temp_set);
            temp_set.setColor(Color.rgb(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
            dataSets.add(temp_set);

        }//for(3)
        LineData data = new LineData(dataSets);
        lineChart.setData(data);
    }//Data_From_External()
    private  Float[] convert_StringArray_to_FloatArray(String[]original) {
        Float[] float_array = null;

        if (original != null) {
            float_array = new Float[original.length];

            try {
                for (int i = 0; i < original.length; i++) {
                    float_array[i] = Float.parseFloat(original[i]);
                }//for(遍歷轉換)
            } //try()
            catch (NumberFormatException e) {

            }//catch()
        }//if(original非空值轉換)

        return float_array;
    }//convert_StringArray_to_FloatArray()
    private  void Animation (LineDataSet set){
        lineChart.animateX(2500);
        lineChart.animateY(2500);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //後臺繪製
        lineChart.setOnChartValueSelectedListener(chartValueSelectedListener);
        lineChart.setDrawGridBackground(false);
        //設定支援觸控手勢
        lineChart.setTouchEnabled(true);
        //設定縮放
        lineChart.setDragEnabled(false);
        //設定推動
        lineChart.setScaleEnabled(false);
        //如果禁用,擴充套件可以在x軸和y軸分別完成
        lineChart.setPinchZoom(true);
    }//Animation()
    private OnChartValueSelectedListener chartValueSelectedListener = new OnChartValueSelectedListener(){

        @Override
        public void onValueSelected(Entry e, Highlight h) {
            MyMarkerView myMarkerView = new MyMarkerView(Line_Chart.this);
            myMarkerView.setChartView(lineChart);
            lineChart.setMarker(myMarkerView);
        }//選中//區塊內程式碼需將 lineChart.setTouchEnabled(true);否則數值無效

        @Override
        public void onNothingSelected() {

        }//未選擇
    } ;


}//main()