package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QR extends AppCompatActivity {
    private Button change,scan_qr;
    private EditText text;
    private ImageView show_qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r);
        FindID();
    }//onCreate()
    private  void  FindID(){
        show_qr=(ImageView) findViewById(R.id.show_qr);
        text=(EditText) findViewById(R.id.qr_text);
        change=(Button) findViewById(R.id.change);
        scan_qr=(Button) findViewById(R.id.scan_qr);
        change.setOnClickListener(btnListener);
        scan_qr.setOnClickListener(btnListener);
    }//FindID()
    private  void  get_QR(){
        BarcodeEncoder encoder = new BarcodeEncoder();
        try {
            Bitmap bit = encoder.encodeBitmap(text.getText().toString(), BarcodeFormat.QR_CODE,190 ,190 );
            show_qr.setImageBitmap(bit);
        }//try()
        catch (WriterException e){e.printStackTrace();}


    }//get_QR
    private View.OnClickListener btnListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.change){ get_QR();}//change()
            if (v.getId()==R.id.scan_qr){
                Intent intent = new Intent(QR.this, SCAN_QR.class);
                startActivity(intent);
            }//scan_qr()
        }//onClick()
    };//btnListener()
}//main()