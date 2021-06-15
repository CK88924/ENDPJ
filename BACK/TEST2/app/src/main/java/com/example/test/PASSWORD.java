package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class PASSWORD extends AppCompatActivity {
    private final static String IvAES = "yYURPnvQepet4c9h";
    private final static String KeyAES = "tSMhhnVeZVxKqd5eM5RdmCwkvWFDsx7U";
    private EditText pass;
    private Button Encrypt,Decrypt;
    private  String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_a_s_s_w_o_r_d);
        FindId();
    }// onCreate()

    private  void  FindId(){
        pass = (EditText) findViewById(R.id.pass);
        Encrypt = (Button) findViewById(R.id.Encrypt);
        Decrypt = (Button) findViewById(R.id.Decrypt);
        Encrypt.setOnClickListener(btnListener);
        Decrypt.setOnClickListener(btnListener);

    }//FindId()

    private View.OnClickListener btnListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (v.getId()==R.id.Encrypt){
                if(!pass.getText().toString().equals("")){
                    try {
                        byte[] TextByte = EncryptAES(IvAES.getBytes("UTF-8"),
                                KeyAES.getBytes("UTF-8"),
                                pass.getText().toString().getBytes("UTF-8"));
                                text = Base64.encodeToString(TextByte, Base64.DEFAULT);
                                setTitle(text);




                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }//pass!=空白
                else{
                    setTitle("請重新輸入");
                }


            }//Encrypt()
            if (v.getId()==R.id.Decrypt){
                byte[] TextByte = new byte[0];

                    try {
                        TextByte = DecryptAES(IvAES.getBytes("UTF-8"),
                                KeyAES.getBytes("UTF-8"),
                                Base64.decode(text.getBytes("UTF-8"), Base64.DEFAULT));
                        String dec_text = new String (TextByte,"UTF-8");
                        setTitle(dec_text);
                    } catch (UnsupportedEncodingException e) {

                        e.printStackTrace();
                    }


            }//Decrypt()

        }//onClick()

    };//btnListener()
    private boolean fileIsExists(String strFile)
    {
        try
        {
            File f=new File(strFile);
            if(!f.exists())
            {
                return false;
            }

        }//try()
        catch (Exception e)
        {
            return false;
        }//catch()

        return true;
    }//fileIsExists()
    private  void  Write(String string){
        try {
            FileOutputStream fos = openFileOutput("encrypt.txt", Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
            osw.write(string);

            osw.flush();
            fos.flush();  //flush是为了输出缓冲区中所有的内容

            osw.close();
            fos.close();  //写入完成后，将两个输出关闭

        }//try()
        catch (FileNotFoundException e) { e.printStackTrace(); }//catch1
        catch (UnsupportedEncodingException e) { e.printStackTrace(); }//catch2
        catch (IOException e) { e.printStackTrace(); }//catch3


    }//Write()
    private  String  Read(){
        try {
            FileInputStream fis = openFileInput("encrypt.txt");
            InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
            char[] input = new char[fis.available()];  //available()用于获取filename内容的长度
            isr.read(input);  //读取并存储到input中

            isr.close();
            fis.close();//读取完成后关闭

            String str = new String(input); //将读取并存放在input中的数据转换成String输出
            return  str;

        }
        catch (FileNotFoundException e) {e.printStackTrace();}//catch1
        catch (UnsupportedEncodingException e) {e.printStackTrace();}//catch2
        catch (IOException e) { e.printStackTrace();}//catch3


    return null;
    }//Read()

    private   byte[] EncryptAES(byte[] iv, byte[] key,byte[] text){
        try {
            AlgorithmParameterSpec mAlgorithmParameterSpec = new IvParameterSpec(iv);
            SecretKeySpec mSecretKeySpec = new SecretKeySpec(key, "AES");
            Cipher mCipher = null;
            mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            mCipher.init(Cipher.ENCRYPT_MODE,mSecretKeySpec,mAlgorithmParameterSpec);

            return mCipher.doFinal(text);
        }//try()
        catch (Exception e){ return null; }//catch()
    }//EncryptAES()

    private byte[] DecryptAES(byte[] iv,byte[] key,byte[] text){
        try {
            AlgorithmParameterSpec mAlgorithmParameterSpec = new IvParameterSpec(iv);
            SecretKeySpec mSecretKeySpec = new SecretKeySpec(key, "AES");
            Cipher mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            mCipher.init(Cipher.DECRYPT_MODE,
                    mSecretKeySpec,
                    mAlgorithmParameterSpec);

            return mCipher.doFinal(text);
        }//try()
        catch (Exception e){return  null; }//catch()

    }// DecryptAES()
}//main()