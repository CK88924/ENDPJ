package com.example.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;


public class MyMarkerView extends MarkerView {
    private TextView see_value;
    private com.github.mikephil.charting.formatter.IAxisValueFormatter IAxisValueFormatter;
    private DecimalFormat format;

    public MyMarkerView(Context context) {
        super(context, R.layout.marker_view);    //第二個引數為佈局
        see_value = findViewById(R.id.see_value);
        format = new DecimalFormat("##0");
    }//MyMarkerView()
    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        see_value.setText(format.format(e.getY()));
        super.refreshContent(e, highlight);
    }//refreshContent
    public MPPointF getOffset(){
        return  new MPPointF(-(getWidth()/2),-getHeight());
    }//getOffset
}// MyMarkerView()
