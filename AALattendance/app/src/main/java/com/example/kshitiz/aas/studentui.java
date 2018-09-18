package com.example.kshitiz.aas;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class studentui extends Fragment {
Button b;
String user,pass;
ProgressBar pbs;
TextInputEditText tietuser,tietpass;
TextInputLayout tiluser,tilpass;
    public studentui() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup v=(ViewGroup)inflater.inflate(R.layout.fragment_studentui, container, false);
        b=v.findViewById(R.id.logins);
        tietuser=v.findViewById(R.id.suser);
        tietpass=v.findViewById(R.id.spass);
        tiluser=v.findViewById(R.id.tiluser);
        tilpass=v.findViewById(R.id.tilpass);
        pbs=v.findViewById(R.id.pbs);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               user=tietuser.getText().toString();
               pass=tietpass.getText().toString();
               new slogin(getString(R.string.url)).execute();
                }
            });
        return v;
    }
    public class slogin extends AsyncTask<Void,Void,String> {
        String u;
        String data="";
        public slogin(String url){
            this.u=url;
        }

        @Override
        protected void onPreExecute() {
            pbs.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(u.concat("logins?id="+user+"&pass="+pass));
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
        protected void onPostExecute(String s) {
            pbs.setVisibility(View.INVISIBLE);

            try{
                JSONObject jo=new JSONObject(s);
                boolean ss=jo.getBoolean("status");
                if(ss){
                    Intent i=new Intent(getActivity(),StudentDashboard.class);
                    i.putExtra("id",user);
                    startActivity(i);
                }
                else{
                    tiluser.setError("");
                    Toast.makeText(getActivity(),"Invalid Username Password Combination",Toast.LENGTH_LONG).show();
                }
            }catch(Exception e){e.printStackTrace();}
        }
    }
}
