package com.example.kshitiz.aas;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.List;

public class FacultyDashboard extends AppCompatActivity {
Button start,done;
TextView tv;
ListView lv;
String id;
ProgressBar fpbar;
List<attitem> at;
customlist sl;
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
        setContentView(R.layout.activity_faculty_dashboard);
        start=findViewById(R.id.start);
        done=findViewById(R.id.listDone);
        tv=findViewById(R.id.ftitle);
        lv=findViewById(R.id.attlist);
        fpbar=findViewById(R.id.fpbar);
        at=new ArrayList<>();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new markandget(getString(R.string.url)+"recognize?faculty=").execute();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done.setVisibility(View.GONE);
                lv.setVisibility(View.GONE);
                start.setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);
            }
        });
    }
    public class markandget extends AsyncTask<Void,Void,String>{
        String u;
        String data="";
        public markandget(String url){
            this.u=url;
        }

        @Override
        protected void onPreExecute() {
            fpbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{


                URL url = new URL(u.concat(id));
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
                data="";
                while((st= in.readLine())!=null)
                    data=data.concat(st);
                conn.disconnect();
            }catch (Exception e){e.printStackTrace();}
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jo = new JSONObject(s);
                if (jo.getString("status").contains("already")) {
                    Toast.makeText(getApplicationContext(),"Attendance already Taken",Toast.LENGTH_SHORT).show();
                } else {
                    fpbar.setVisibility(View.GONE);
                    start.setVisibility(View.GONE);
                    tv.setVisibility(View.GONE);
                    done.setVisibility(View.VISIBLE);
                    lv.setVisibility(View.VISIBLE);
                    JSONArray ja = jo.getJSONArray("attstatus");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jj = ja.getJSONObject(i);
                        at.add(new attitem(jj.getString("name"), jj.getBoolean("status")));
                    }
                    lv.setAdapter(new customlist(getApplicationContext(),R.layout.child,at));
                }
                }catch(Exception e){
                    e.printStackTrace();
                }
        }
    }
}
