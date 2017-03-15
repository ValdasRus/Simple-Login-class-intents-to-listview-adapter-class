package com.example.valdas.loginas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Arrays;


import static com.example.valdas.loginas.User.naujas;
import static com.example.valdas.loginas.User.pressed1;



public class Login extends AppCompatActivity {

    static int[] pressed = {-1};
    final static String[] initial = new String[2];
    int counter = 5;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(pressed1 == true) {
            Intent mIntent = getIntent();
            int value = mIntent.getIntExtra("pressed[0]", 0);
            String[] ren = mIntent.getStringArrayExtra("name");
            pressed[0] = value;
            initial[0] = ren[0];
            initial[1] = ren[1];
            if (pressed[0] == 0) {
                Log.d("aaa", "aaaa");
            }
        }
        

        final EditText etName = (EditText) findViewById(R.id.editText);
        final EditText etPassword = (EditText) findViewById(R.id.editText2);
        final TextView tvLeft = (TextView) findViewById(R.id.textView5);
        final Button btLogin = (Button) findViewById(R.id.button);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pressed[0] == -1) {
                    initial[0] = etName.getText().toString();
                    initial[1] = etPassword.getText().toString();
                    Log.d("button", "Sekmingai uzsiregistravote - prisijunkite");
                    Toast.makeText(Login.this, "Registered successfully, please Log-in", Toast.LENGTH_SHORT).show();
                    pressed[0] = 0;

                    btLogin.setText("LOGIN");
                    etName.getText().clear();
                    etPassword.getText().clear();
                }else {
                    if (pressed[0] == 0) {
                        String name = etName.getText().toString();
                        String pass = etPassword.getText().toString();
                        if (initial[0].equals(name) && initial[1].equals(pass)) {
                            Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, User.class);
                            intent.putExtra("name", initial[0]);
                            intent.putExtra("password", initial[1]);
                            Login.this.startActivity(intent);
                            etName.getText().clear();
                            etPassword.getText().clear();

                        } else {
                            Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            counter--;
                            tvLeft.setText(Integer.toString(counter));
                            etName.getText().clear();
                            etPassword.getText().clear();
                            if (counter == 0) {
                                btLogin.setEnabled(false);
                                Toast.makeText(Login.this, "Access Blocked", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            }
        });

    }
}
