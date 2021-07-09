package com.example.test;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;

public class MyMarkerView2 extends MarkerView {
    private TextView show_data;
    private ImageView image_item;
    private DecimalFormat format;
    public MyMarkerView2(Context context) {
        super(context, R.layout.marker_view2);
        show_data = findViewById(R.id.show_data);
        image_item = findViewById(R.id.image_item);
        format = new DecimalFormat("##0");
    }//MyMarkerView2()
    public void refreshContent(Entry e, Highlight highlight) {
        show_data.setText(format.format(e.getY()));
        if((int)highlight.getX()< 3 ){image_item.setImageResource(R.drawable.b);}
        else if((int)highlight.getX()< 6 ){image_item.setImageResource(R.drawable.c);}
        else if((int)highlight.getX()< 9 ){image_item.setImageResource(R.drawable.d);}
        else if((int)highlight.getX()<=11 ){image_item.setImageResource(R.drawable.e);}



        super.refreshContent(e, highlight);
    }//refreshContent

    public MPPointF getOffset(){
        return  new MPPointF(-(getWidth()/2),-getHeight());
    }//getOffset
}//Class MyMarkerView2()
