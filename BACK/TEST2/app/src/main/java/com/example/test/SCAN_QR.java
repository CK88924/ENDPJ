package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class SCAN_QR extends AppCompatActivity {
    private SurfaceView surfaceView;
    private TextView textView;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_c_a_n__q_r);
        get_Camera();
        FindID();
    }// onCreate()
    private  void FindID(){
        surfaceView=(SurfaceView)findViewById(R.id.scan);
        textView=(TextView)findViewById(R.id.show_text);
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource=new CameraSource.Builder(this,barcodeDetector).setRequestedPreviewSize(300,300).build();
        cameraSource = new CameraSource.Builder(this,barcodeDetector).setAutoFocusEnabled(true).build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED){return;}
                try {cameraSource.start(holder); }//try()
                catch (IOException e){e.printStackTrace();}//catch()
            }//surfaceCreated()

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }//surfaceChanged()

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {cameraSource.stop();}//surfaceDestroyed()
        });//SurfaceHolder.Callback()
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }//release()

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes=detections.getDetectedItems();
                if(qrCodes.size()!=0){
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(qrCodes.valueAt(0).displayValue);
                        }
                    });
                }//if(有掃描到條碼)

            }//receiveDetections()
        });//new Detector()

    }//FindID()
    private  void  get_Camera(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);}

    }//get_Camera()
}//main()