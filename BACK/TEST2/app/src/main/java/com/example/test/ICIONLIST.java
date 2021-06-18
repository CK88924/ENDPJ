package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ICIONLIST extends AppCompatActivity {
    private ListView icon_list;
    private String[]name_list = new String[]{"金城凱","沈夢瑤", "袁一琦","吳哲晗","許佳琪"};
    int[]img_list = new int[] {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_c_i_o_n_l_i_s_t);
        FindID();
    }//onCreate()
    private  void  FindID(){
        icon_list = (ListView) findViewById(R.id.iconlist);
        listlayoutadapter adasports=new listlayoutadapter(this);
        icon_list.setAdapter(adasports);
        icon_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ICIONLIST.this, "ID：" + id + "   選單文字："+ icon_list.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG).show();

            }//onItemClick()
        });//OnItemClickListener()

    }//FindID()
    public class listlayoutadapter extends BaseAdapter{
        private LayoutInflater listlayoutInflater;
        public listlayoutadapter(Context c){
             listlayoutInflater= LayoutInflater.from(c);
        }//listlayoutadapter()
        public int getCount() {
            return name_list.length;
        }//getCount()
        public Object getItem(int position) {
            return name_list[position];
        }// getItem()
        public long getItemId(int position) {
            return position;
        }//getItemId()

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = listlayoutInflater.inflate(R.layout.image_item,null);
            ImageView icon = (ImageView) convertView.findViewById(R.id.image_icon);
            TextView name = (TextView) convertView.findViewById(R.id.image_name);
            name.setText(name_list[position]);
            icon.setImageResource(img_list[position]);
            return convertView;

        }//getView()

    }//listlayoutadapter
}//main()