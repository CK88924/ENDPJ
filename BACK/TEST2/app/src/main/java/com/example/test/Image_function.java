package com.example.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Image_function extends AppCompatActivity {
    private Button image_load, image_upload, image_read, image_delete;
    private ImageView Image_Show;
    private StorageReference storageRef,pic_storage;
    private Intent intent;
    private Uri uri;
    private EditText image_name;
    private  String data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_functiom);
        FindId();
    }//onCreate()
    private  void FindId(){
        image_load = (Button) findViewById(R.id.image_load);
        image_upload = (Button) findViewById(R.id.image_upload);
        image_read = (Button) findViewById(R.id.image_read);
        image_delete = (Button) findViewById(R.id.image_delete);
        Image_Show = (ImageView) findViewById(R.id.Image_Show);
        image_name =(EditText) findViewById(R.id.image_name);
        storageRef = FirebaseStorage.getInstance().getReference();

        image_load.setOnClickListener(btnListener);
        image_upload.setOnClickListener(btnListener);
        image_read.setOnClickListener(btnListener);
        image_delete.setOnClickListener(btnListener);
    }//FindId()
    private View.OnClickListener btnListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (v.getId() == R.id.image_load) {
                intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);



            }//if(image_load)
            if (v.getId() == R.id.image_upload) {
                SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                Date current = new Date();
                pic_storage =storageRef.child(sdFormat.format(current));
                pic_storage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        setTitle("上傳成功");
                        Image_Show.setImageURI(null);

                    }//onSuccess()
                });//OnSuccessListener()

            }//if(image_upload)
            if (v.getId() == R.id.image_read) {
                IMAGE_READ(image_name.getText().toString());


            }//if(image_read)
            if (v.getId() == R.id.image_delete) {

            }//if(image_delete)

        }//onClick()
    };//btnListener()
    private  void  IMAGE_READ(String pic_name){
        Image_Show.setImageURI(null);
        pic_storage = storageRef.child(pic_name);
        File file;
        try{
        file =File.createTempFile("images","jpg");
        pic_storage.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Image_Show.setImageURI(Uri.fromFile(file));
                setTitle("讀取成功");

            }//onSuccess()
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                setTitle("讀取失敗");

            }//onFailure
        });//Listener
        }//try()
        catch (IOException e){e.printStackTrace();}//catch()


    }//IMAGE_READ()


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1){
            uri = data.getData();
            Image_Show.setImageURI(uri);
            ContentResolver contentResolver = getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            data_list =mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        }//if()
        super.onActivityResult(requestCode, resultCode, data);
    }
}//main()