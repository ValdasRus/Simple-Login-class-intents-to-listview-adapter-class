package com.example.valdas.loginas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class EditActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;

    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        textView = (TextView) findViewById(R.id.textView6);
        editText = (EditText) findViewById(R.id.editText5);
        button = (Button) findViewById(R.id.button4);

        SharedPreferences settings = getSharedPreferences("PREFS",0);

        text = settings.getString("pos", "");

        textView.setText(text);
        editText.setText(text);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String up = editText.getText().toString();

                SharedPreferences settings = getSharedPreferences("PREFS",0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("pos", "up");
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), User.class);
                startActivity(intent);
            }
        });
    }
}

