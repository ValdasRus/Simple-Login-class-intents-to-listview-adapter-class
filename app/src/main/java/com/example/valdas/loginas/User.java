package com.example.valdas.loginas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.valdas.loginas.Login.initial;


public class User extends AppCompatActivity {

    static boolean pressed1 = false;
    static String[] naujas = new String[2];

    final int[] al = new int[1];

    CustomListViewAdapter adapter;

    public static final String[] titles = new String[1];
    public static final Bitmap[] images = new Bitmap[2];

    ListView listView;
    List<RowItem> rowItems;

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        images[1] = BitmapFactory.decodeResource(User.this.getResources(), R.drawable.ic_launcher);
        images[0] = images[1];

        rowItems = new ArrayList<>();



        listView = (ListView) findViewById(R.id.listView);
        adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
        listView.setAdapter(adapter);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                al[0] = position;
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Long Press For Edit" ,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

                ImageView imgView = (ImageView) view.findViewById(R.id.icon);
                imgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                    }
                });
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String pos1 = String.valueOf(rowItems.get(position));

                SharedPreferences settings = getSharedPreferences("PREFS",0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("pos", pos1);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                startActivity(intent);

                return true;

            }
        });

        Button btLogout = (Button) findViewById(R.id.button2);
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pressed1 = true;

                Toast.makeText(User.this, "Logged off", Toast.LENGTH_SHORT).show();

                naujas[0] = initial[0];
                naujas[1] = initial[1];

                Intent intent = new Intent(User.this, Login.class);
                intent.putExtra("pressed[0]", 0);
                intent.putExtra("name", naujas);
                startActivity(intent);
            }
        });

        Button btnAdd = (Button) findViewById(R.id.button5);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = (EditText) findViewById(R.id.editText3);
                titles[0] = editText.getText().toString();

                final RowItem item = new RowItem(images[0], titles[0]);
                rowItems.add(item);
                adapter.notifyDataSetChanged();
                editText.getText().clear();

            }
        });

        Button btnDel = (Button) findViewById(R.id.button3);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rowItems.size() == 0){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Nothing to delete" ,
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }else {
                    rowItems.remove(al[0]);
                    adapter.notifyDataSetChanged();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);
                images[0] = bitmap;

                ImageView imageView = (ImageView) listView.getChildAt(al[0]).findViewById(R.id.icon);
                imageView.setImageBitmap(images[0]);

                TextView textView = (TextView) listView.getChildAt(al[0]).findViewById(R.id.title);
                titles[0] = textView.getText().toString();



                final RowItem item = new RowItem(images[0], titles[0]);
                rowItems.set(al[0], item);


                adapter.notifyDataSetChanged();
                images[0] = images[1];



            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }



}








