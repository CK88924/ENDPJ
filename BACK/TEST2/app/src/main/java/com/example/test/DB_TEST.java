package com.example.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DB_TEST extends AppCompatActivity {
    private EditText insert_data;
    private Button insert1,insert2, search1,search2;
    private ListView view_list;
    private Vector<String>data_list = new Vector<String>();
    private  String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_test);
        FindId();
    }//onCreate()
    private void FindId(){
        insert_data = (EditText) findViewById(R.id.insert_data);
        insert1 = (Button) findViewById(R.id.insert1);
        insert2 = (Button) findViewById(R.id.insert2);
        search1 = (Button) findViewById(R.id.search1);
        search2 = (Button) findViewById(R.id.search2);
        insert1.setOnClickListener(btnListener);
        insert2.setOnClickListener(btnListener);
        search1.setOnClickListener(btnListener);
        search2.setOnClickListener(btnListener);

        view_list = (ListView) findViewById(R.id.view_list);
    }//FindId()
    private View.OnClickListener btnListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (v.getId() == R.id.insert1) {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
                String date_str = format.format(date);
                String data_str = insert_data.getText().toString();
                if(!data_str.equals("")&&data_str!=null) {
                    Data1 item = new Data1(data_str, date_str);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference newData = database.getReference("DATA");
                    newData.push().setValue(item);
                    setTitle("即時新增成功");
                }//if(有新增值)
                else{setTitle("請輸入新增值");}
            }//if(即時新增)
            if (v.getId() == R.id.insert2) {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
                String date_str = format.format(date);
                String data_str = insert_data.getText().toString();
                if(!data_str.equals("") && data_str!=null) {
                    Data2 item = new Data2(data_str, date_str);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference newData = db.collection("DATA").document();
                    newData.set(item);
                    setTitle("雲端新增成功");
                }//if(有新增值)
                else{setTitle("請輸入新增值");}
            }//if(雲端新增)
            if (v.getId() == R.id.search1) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference Ref = database.getReference("DATA");
                Ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        data_list.clear();
                        EditText search_data = (EditText) findViewById(R.id.search_data);;
                        String search_str = search_data.getText().toString();
                        if(!search_str.equals("") &&search_str !=null ) {
                            for (DataSnapshot contact : snapshot.getChildren()) {
                                Pattern pattern = Pattern.compile(search_str);
                                Matcher matcher = pattern.matcher(snapshot.child(contact.getKey()).child("data").getValue().toString());
                                if(matcher.find()){
                                    data_list.add(snapshot.child(contact.getKey()).child("data").getValue().toString());

                                }//if(有符合)
                            }//for
                        }//if(搜尋值非空白和 null)
                        else{setTitle("請輸入搜尋字串");}
                        ArrayAdapter listAdapter = new ArrayAdapter<String>(DB_TEST.this, android.R.layout.simple_list_item_1,data_list);
                        view_list.setAdapter(listAdapter);


                    }
                    @Override
                    public void onCancelled( DatabaseError error) {

                    }
                });


            }//if(即時搜尋)
            if (v.getId()==R.id.search2){

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("DATA")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            data_list.clear();
                            EditText search_data = (EditText) findViewById(R.id.search_data);;
                            String search_str = search_data.getText().toString();
                            if(!search_str.equals("") &&search_str !=null ) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Pattern pattern = Pattern.compile(search_str);
                                    Matcher matcher = pattern.matcher(document.get("data").toString());
                                    if(matcher.find()){
                                        data_list.add(document.get("data").toString());

                                    }//if(有符合)

                                }//for(遍歷DATA)
                            }//if(搜尋值非空白和 null)
                            else{setTitle("請輸入搜尋值");}
                            ArrayAdapter listAdapter = new ArrayAdapter<String>(DB_TEST.this, android.R.layout.simple_list_item_1,data_list);
                            view_list.setAdapter(listAdapter);

                        }//if(task.isSuccessful())

                    }
                });

            }//if(雲端搜尋)

        }//onClick()

    };//btnListener()
    public  class Data1{
        private  String data;
        private  String time;
        public Data1(String data, String time){
            this.data = data;
            this.time =time;
        }//Data1()

        public String getData() {
            return this.data =data;
        }//getData()

        public String getTime() {
            return this.time =time;
        }//getTime()
    }//class Data1

    public class Data2{
        private  String data;
        private  String time;
        public Data2(String data, String time){
            this.data = data;
            this.time =time;
        }//Data1()

        public String getData() {
            return this.data =data;
        }//getData()

        public String getTime() {
            return this.time =time;
        }//getTime()
    }//class Data1
}//main()
