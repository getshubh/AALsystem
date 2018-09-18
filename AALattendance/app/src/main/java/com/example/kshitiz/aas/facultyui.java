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
public class facultyui extends Fragment {
Button b;
String id;
ProgressBar pbf;
String user,pass;
    TextInputEditText tietuser,tietpass;
    TextInputLayout tiluser,tilpass;
    public facultyui() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup v=(ViewGroup)inflater.inflate(R.layout.fragment_facultyui, container, false);
        b=v.findViewById(R.id.loginf);
        tietuser=v.findViewById(R.id.fuser);
        tietpass=v.findViewById(R.id.fpass);
        tiluser=v.findViewById(R.id.tiluserf);
        tilpass=v.findViewById(R.id.tilpassf);
        pbf=v.findViewById(R.id.pbf);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user=tietuser.getText().toString();
                pass=tietpass.getText().toString();
                new flogin(getString(R.string.url)).execute();
            }
        });
        return v;
    }
    public class flogin extends AsyncTask<Void,Void,String>{
        String u;
        String data="";
        public flogin(String url){
            this.u=url;
        }

        @Override
        protected void onPreExecute() {
            pbf.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(u.concat("loginf?id="+user+"&pass="+pass));
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
            pbf.setVisibility(View.INVISIBLE);

            try{
                JSONObject jo=new JSONObject(s);
                boolean ss=jo.getBoolean("status");
                if(ss){
                    Intent i=new Intent(getActivity(),FacultyDashboard.class);
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
