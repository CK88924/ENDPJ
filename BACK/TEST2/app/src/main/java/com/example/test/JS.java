package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class JS extends AppCompatActivity {
    private Button num_insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js);
        FindId();
    }//onCreate()
    private  void FindId(){
        num_insert = (Button) findViewById(R.id.num_insert);
        num_insert.setOnClickListener(btnListener);
    }//FindId()
    private View.OnClickListener btnListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (v.getId() == R.id.num_insert) {
                Random r = new Random();
                String num = String.valueOf(r.nextInt(500)+1);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference newNum = database.getReference("Num");
                newNum.push().child("num").setValue(num);
            }//if(num_insert)
        }//onClick()
    };//btnListener()
}//main()