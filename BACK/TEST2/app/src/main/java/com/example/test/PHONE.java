package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Random;

public class PHONE extends AppCompatActivity {
    private EditText country_num,phone_num,message,check;
    private ImageButton call;
    private Button sms,verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_h_o_n_e);
        get_permission1();
        get_permission2();
        get_permission3();
        FindID();
    }//onCreate()

    private  void  FindID(){
        country_num = (EditText) findViewById(R.id.country_num);
        phone_num = (EditText) findViewById(R.id.phone_num);
        message = (EditText) findViewById(R.id.messages);
        check = (EditText) findViewById(R.id.check);
        call = (ImageButton) findViewById(R.id.call);
        sms = (Button) findViewById(R.id.sms);
        verification = (Button) findViewById(R.id.verification);
        call.setOnClickListener(btnListener);
        sms.setOnClickListener(btnListener);
        verification.setOnClickListener(btnListener);
    }//FindID()

    private View.OnClickListener btnListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.call){
                if(country_num.getText().toString().equals("")||phone_num.getText().toString().equals("")){
                    Toast.makeText(PHONE.this,"請輸入國碼及電話號碼",
                            Toast.LENGTH_LONG).show();
                }//if(空值)
                PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();//電話物件初始化
                try {
                    Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(
                            "+"+country_num.getText().toString()+phone_num.getText().toString(),
                            country_num.getText().toString());//+886 886970633857
                    boolean isValid = phoneUtil.isValidNumber(phoneNumber);
                    if(isValid==true){
                        String phone_str = phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" +phone_str);
                        intent.setData(data);
                        startActivity(intent);


                    }//if(有效電話)
                }//try()
                catch (NumberParseException e){e.printStackTrace(); }


            }//if(call)
            if (v.getId()==R.id.sms){
                if(country_num.getText().toString().equals("")
                        ||phone_num.getText().toString().equals("")
                        ||message.getText().toString().equals("")){
                    Toast.makeText(PHONE.this,"請輸入國碼及電話號碼及簡訊訊息",
                            Toast.LENGTH_LONG).show();
                }//if(空值)
                PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();//電話物件初始化
                try {
                    Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(
                            "+" + country_num.getText().toString() + phone_num.getText().toString(),
                            country_num.getText().toString());
                    boolean isValid = phoneUtil.isValidNumber(phoneNumber);
                    if (isValid == true) {
                        String phone_str = phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
                        String text =message.getText().toString();
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phone_str, null, text, null, null);


                    }//if(有效電話)
                }//try()
                catch (NumberParseException e){e.printStackTrace(); }


            }//if(sms)
            if (v.getId()==R.id.verification){
                if(country_num.getText().toString().equals("")
                        ||phone_num.getText().toString().equals("")){
                    Toast.makeText(PHONE.this,"請輸入國碼及電話號碼",
                            Toast.LENGTH_LONG).show();
                }//if(空值)
                PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();//電話物件初始化
                try {
                    Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(
                            "+" + country_num.getText().toString() + phone_num.getText().toString(),
                            country_num.getText().toString());
                    boolean isValid = phoneUtil.isValidNumber(phoneNumber);
                    if (isValid == true) {
                        Random r1 = new Random();
                        String ps="";
                        for(int i=0; i < 4 ; i++){
                            int n =r1.nextInt(10);
                            ps= ps +String.valueOf(n);
                        }//for
                        String phone_str = phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phone_str, null, "驗證碼:"+ ps, null, null);


                    }//if(有效電話)
                }//try()
                catch (NumberParseException e){e.printStackTrace(); }


            }//if(verification)

        }//onClick()
    };//btnListener()
    private  void  get_permission1(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
        }//if(電話)



    }//get_permission()
    private void get_permission2(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
        }//if(電話狀態)
    }//get_permission2
    private  void  get_permission3(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        }//if(簡訊)
    }//get_permission3
}//main()