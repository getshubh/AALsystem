package com.example.kshitiz.aasadmin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class adminpanel extends AppCompatActivity {
EditText e;
TextInputLayout til;
Button b1,b2;
ImageButton ib;
String name;
String url=null;
ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);
        e=findViewById(R.id.stname);
        til=findViewById(R.id.name);
        pb=findViewById(R.id.progressbar);
        b1=findViewById(R.id.train);
        b2=findViewById(R.id.register);
        ib=findViewById(R.id.sendvalue);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                til.setVisibility(View.VISIBLE);
                ib.setVisibility(View.VISIBLE);
            }
        });
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = e.getText().toString();
                ib.setVisibility(View.GONE);
                til.setVisibility(View.GONE);
                if (name.equals("")) {
                    Toast.makeText(adminpanel.this, "enter a name", Toast.LENGTH_LONG).show();
                } else {
                        getdata gd = new getdata(name, getString(R.string.url)+"record?name=");
                        gd.execute();
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new train(getString(R.string.url)+"train").execute();
            }
        });
    }
    public class train extends AsyncTask<Void,Void,Void>{
        String url;
        public train(String url){
            this.url=url;
        }

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
            Toast.makeText(adminpanel.this,"Recognition Model is being trained",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(this.url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                    Log.d("fail", "Failed");
                }
            }catch (Exception e){}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pb.setVisibility(View.GONE);
            Toast.makeText(adminpanel.this,"Model Trained Successfully",Toast.LENGTH_LONG).show();
        }
    }
    public class getdata extends AsyncTask<Void,Void,String>{
    String name;
    String u;
        public getdata(String name,String u){
            this.name=name;
            this.u=u;
        }

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"Updating database Stay still for proper recognition",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result="";
            try{
                String json=name;

                URL url = new URL(u+json);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                    Log.d("fail","Failed");
                }
                // read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String st;
                while((st= in.readLine())!=null)
                    result=result.concat(st);

                Log.d("ttttttt",result);
                conn.disconnect();
            }catch (Exception e){e.printStackTrace();}
            return result;
        }

        @Override
        protected void onPostExecute(String jsonObject) {
            pb.setVisibility(View.GONE);
            int id=0;
            String passw="";
            try{
                id=new JSONObject(jsonObject).getInt("ID");
                passw=new JSONObject(jsonObject).getString("passw");
            }catch (Exception e){e.printStackTrace();}
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(adminpanel.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(adminpanel.this);
            }
            builder.setTitle("Information").setMessage("Student ID is: "+id+"\nPassword is: "+passw).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }).show();
        }
    }
}
