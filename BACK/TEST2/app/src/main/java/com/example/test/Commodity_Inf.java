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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;

public class Commodity_Inf extends AppCompatActivity {
    private EditText email,pass,product_name;
    private ImageView imageView,imageView2,imageView3,imageView4,imageView5,imageView6;
    private Button send,set;
    private FirebaseAuth mAuth;
    private StorageReference storageRef,pic_storage;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Intent intent;
    private Uri uri;
    private  Uri imageUri = Uri.parse("android.resource://com.example.test/drawable/cat");
    private Uri[] store_Uri = new Uri[6];
    private  String data_list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_inf);
        FindId();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                // ...
            }//onAuthStateChanged()
        };//AuthStateListener()
    }//onCreate()
    private  void  FindId(){
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        product_name = (EditText) findViewById(R.id.product_name);
        send = (Button) findViewById(R.id.send);
        set = (Button) findViewById(R.id.set);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        imageView5 = (ImageView) findViewById(R.id.imageView5);
        imageView6 = (ImageView) findViewById(R.id.imageView6);
        imageView.setImageURI(imageUri);
        imageView2.setImageURI(imageUri);
        imageView3.setImageURI(imageUri);
        imageView4.setImageURI(imageUri);
        imageView5.setImageURI(imageUri);
        imageView6.setImageURI(imageUri);
        send.setOnClickListener(btnListener);
        set.setOnClickListener(btnListener);
        imageView.setOnClickListener(btnListener);
        imageView2.setOnClickListener(btnListener);
        imageView3.setOnClickListener(btnListener);
        imageView4.setOnClickListener(btnListener);
        imageView5.setOnClickListener(btnListener);
        imageView6.setOnClickListener(btnListener);
        storageRef = FirebaseStorage.getInstance().getReference();
        Arrays.fill( store_Uri, imageUri );
    }//FindId()
    private View.OnClickListener btnListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.send){Register();}//send()
            if (v.getId()==R.id.set){
                Set_product();

                 }//set()
            if(v.getId()==R.id.imageView){
                intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }//imageView()
            if(v.getId()==R.id.imageView2){
                intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,2);
            }//imageView2()
            if(v.getId()==R.id.imageView3){
                intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,3);
            }//imageView3()
            if(v.getId()==R.id.imageView4){
                intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,4);
            }//imageView4()
            if(v.getId()==R.id.imageView5){
                intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,5);
            }//imageView5()
            if(v.getId()==R.id.imageView6){
                intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,6);
            }//imageView6()

        }//onClick()
    };//btnListener()
    private  void Register(){
        String get_email = email.getText().toString();
        String get_password  = pass.getText().toString();
        if(get_email.matches("") || get_password.matches("") ){
            Toast.makeText(Commodity_Inf.this ,  "請填寫完整資料", Toast.LENGTH_LONG).show();

        }//if(空值)
        else{
            mAuth.createUserWithEmailAndPassword(get_email,get_password).addOnCompleteListener(new OnCompleteListener() {

                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        String uid=mAuth.getCurrentUser().getUid();//取得使用者UID
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ContactsRef = database.getReference("Member");
                        account Account =new account(get_email,get_password);
                        ContactsRef.child(uid).setValue(Account);
                        Toast.makeText(Commodity_Inf.this ,  "新增帳戶成功", Toast.LENGTH_LONG).show();
                    }//if(建立註冊成功)

                }//onComplete()
            });//OnCompleteListener()

        }//else(建立註冊)

    }//Register()

    private  void Set_product(){
        String get_email = email.getText().toString();
        String get_password  = pass.getText().toString();
        String get_product_name =product_name.getText().toString();
        if(get_email.matches("") || get_password.matches("")||get_product_name.matches("") ){
            Toast.makeText(Commodity_Inf.this ,  "請填寫完整資料", Toast.LENGTH_LONG).show();

        }//if(空值)
        else{
            mAuth.signInWithEmailAndPassword(get_email, get_password).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        String uid=mAuth.getCurrentUser().getUid();//取得使用者UID
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ContactsRef = database.getReference("Member").child(uid);
                        String key = ContactsRef.child("product").push().getKey();
                        inf item =new inf(get_product_name,key);
                        ContactsRef.child("product").child(key).setValue(item);
                        if(store_Uri[0]== imageUri){ upload_image(key+"cat_01",store_Uri[0],imageView);}
                        else{upload_image(key+"_01",store_Uri[0],imageView);}

                        if(store_Uri[1]== imageUri){ upload_image(key+"cat_02",store_Uri[1],imageView2);}
                        else{upload_image(key+"_02",store_Uri[1],imageView2);}

                        if(store_Uri[2]== imageUri){ upload_image(key+"cat_03",store_Uri[2],imageView3);}
                        else{upload_image(key+"_03",store_Uri[2],imageView3);}

                        if(store_Uri[3]== imageUri){ upload_image(key+"cat_04",store_Uri[3],imageView4);}
                        else{upload_image(key+"_04",store_Uri[3],imageView4);}

                        if(store_Uri[4]== imageUri){ upload_image(key+"cat_05",store_Uri[4],imageView5);}
                        else{upload_image(key+"_05",store_Uri[4],imageView5);}

                        if(store_Uri[5]== imageUri){ upload_image(key+"cat_06",store_Uri[5],imageView6);}
                        else{upload_image(key+"_06",store_Uri[5],imageView6);}

                        Arrays.fill( store_Uri, imageUri );
                    }//if(登入成功)
                    else{Toast.makeText(Commodity_Inf.this ,  "登入失敗", Toast.LENGTH_LONG).show();}

                }//onComplete()

            });//signInWithEmailAndPassword()

        }//else(登入並新增商品及上傳圖片)

    }//Set_product()
    private  void upload_image(String image_name ,Uri image_uri,ImageView temp_imageView){
        pic_storage =storageRef.child(image_name);
        pic_storage.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Commodity_Inf.this ,  "上傳成功", Toast.LENGTH_LONG).show();
                temp_imageView.setImageURI(null);
                

            }//onSuccess()
        });//OnSuccessListener()

    }//upload_image()

    public class inf {
        private  String product_name;
        private  String key;

        public inf(String product_name,String key){

            this.product_name = product_name;
            this.key = key;

        }//public inf()

        public String getProduct_name() { return this.product_name = product_name; }

        public String getKey() {return this.key = key; }
    }//class inf

    public class account {
        private  String email;
        private  String password;

        public account(String email,String password){

            this.email = email;
            this.password = password;

        }//public account()

        public String getEmail() {
            return this.email =email;
        }

        public String getPassword() {
            return this.password = password;
        }
    }//class account
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1){
            uri = data.getData();
            store_Uri[0]=uri;
            imageView.setImageURI(uri);
            ContentResolver contentResolver = getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            data_list =mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        }//if(1)
        if(requestCode==2){
            uri = data.getData();
            store_Uri[1]=uri;
            imageView2.setImageURI(uri);
            ContentResolver contentResolver = getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            data_list =mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        }//if(2)
        if(requestCode==3){
            uri = data.getData();
            store_Uri[2]=uri;
            imageView3.setImageURI(uri);
            ContentResolver contentResolver = getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            data_list =mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        }//if(3)
        if(requestCode==4){
            uri = data.getData();
            store_Uri[3]=uri;
            imageView4.setImageURI(uri);
            ContentResolver contentResolver = getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            data_list =mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        }//if(4)
        if(requestCode==5){
            uri = data.getData();
            store_Uri[4]=uri;
            imageView5.setImageURI(uri);
            ContentResolver contentResolver = getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            data_list =mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        }//if(5)
        if(requestCode==6){
            uri = data.getData();
            store_Uri[5]=uri;
            imageView6.setImageURI(uri);
            ContentResolver contentResolver = getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            data_list =mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        }//if(6)
        super.onActivityResult(requestCode, resultCode, data);
    }



}//main()