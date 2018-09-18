package com.example.kshitiz.aasadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText user,pass;
Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user=findViewById(R.id.username);
        pass=findViewById(R.id.password);
        b=findViewById(R.id.loginbutton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getText().toString().equals("kshitiz")&&pass.getText().toString().equals("123456"))
                    startActivity(new Intent(MainActivity.this,adminpanel.class));
                else
                    Toast.makeText(getApplicationContext(),"Invalid UserName or Password Combination",Toast.LENGTH_LONG).show();
            }
        });
    }
}
