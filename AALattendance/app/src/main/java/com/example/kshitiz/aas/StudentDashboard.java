package com.example.kshitiz.aas;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentDashboard extends AppCompatActivity {
ExpandableListView elv;
String id;
TextView tv;
ExpandableListAdapter ela;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Button b,d;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null){
            Bundle extras=getIntent().getExtras();
            if(extras==null){
                id=null;
            }else{
                id=extras.getString("id");
            }
        }else{
            id=(String) savedInstanceState.getSerializable("id");
        }
        setContentView(R.layout.activity_student_dashboard);
        elv=findViewById(R.id.listdates);
        b=findViewById(R.id.check);
        pb=findViewById(R.id.spbar);
        tv=findViewById(R.id.studenttitle);
        d=findViewById(R.id.done);
        Toast.makeText(getApplicationContext(),"id="+id,Toast.LENGTH_SHORT).show();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getdata(getString(R.string.url)+"getdata?id=").execute();
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elv.setVisibility(View.GONE);
                d.setVisibility(View.GONE);
                b.setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);
            }
        });
    }
    public class getdata extends AsyncTask<Void,Void,String>{
    String uu;
    String data="";
        public getdata(String url){
            this.uu=url;
        }

        @Override
        protected void onPreExecute() {
            b.setBackgroundColor(Color.RED);
            b.setText("");
            pb.setVisibility(View.VISIBLE);
            listDataHeader=new ArrayList<>();
            listDataChild=new HashMap<>();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{


                URL url = new URL(uu.concat(id));
                System.out.print(url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                    Log.d("fail","Failed");
                }
                // read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String st;
                while((st= in.readLine())!=null)
                    data=data.concat(st);
                conn.disconnect();
            }catch (Exception e){e.printStackTrace();}
            return data;
        }

        @Override
        protected void onPostExecute(String st) {

            b.setBackgroundColor(Color.argb(255,102,153,0));
            b.setText("Check Attendance");
            pb.setVisibility(View.GONE);
            b.setVisibility(View.GONE);
            tv.setVisibility(View.GONE);
            elv.setVisibility(View.VISIBLE);
            d.setVisibility(View.VISIBLE);
            try{
                JSONObject js=new JSONObject(data);
                JSONObject jj=js.getJSONObject("absent");
                JSONArray ja=jj.getJSONArray("Dates");
                for(int i=0;i<ja.length();i++){
                    String dat=ja.get(i).toString();
                    listDataHeader.add(dat);
                    JSONArray jaa=jj.getJSONArray(dat);
                    List<String > child=new ArrayList<>();
                    for(int j=0;j<jaa.length();j++){
                        child.add(jaa.get(j).toString());
                    }
                    listDataChild.put(dat,child);
                }
            }catch(Exception e){e.printStackTrace();Log.d("data",data);}
            if(listDataHeader.size()!=0) {
                ela = new CustomAdapter(getApplicationContext(), listDataHeader, listDataChild);
                elv.setAdapter(ela);
            }
            else
                Toast.makeText(getApplicationContext(),"No Absents",Toast.LENGTH_SHORT).show();;
        }
    }
}
