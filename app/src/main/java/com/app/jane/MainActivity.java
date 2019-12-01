package com.app.jane;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnReg;
    EditText emailval,passwordval;
    TextView createUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Table creation
        SQLiteDatabase mydatabase = openOrCreateDatabase("CHRISTMAS_CAROLS",MODE_PRIVATE,null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS USERS(first_name VARCHAR not null,last_name VARCHAR,email VARCHAR unique,password VARCHAR not null)");
        mydatabase.close();

        emailval=(EditText)findViewById(R.id.emailval);
        passwordval=(EditText)findViewById(R.id.passwordval);
        btnReg=(Button)findViewById(R.id.loginbutton);
        createUser = findViewById(R.id.createuser);

        createUser.setOnClickListener( new View.OnClickListener() {
            @Override
             public void onClick(View argso) {
                Intent intent  = new Intent(MainActivity.this,CreateUser.class);
                startActivity(intent);
                   }
             }

        );
        btnReg.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View argso) {
                                          if (TextUtils.isEmpty(emailval.getText())  && TextUtils.isEmpty(passwordval.getText())) {
                                              showToast("Please enter credentials to Login", Color.BLACK);
                                              Intent intent = new Intent(MainActivity.this, CreateUser.class);
                                              startActivity(intent);
                                          } else {
                                              User user = fetchUser(emailval.getText().toString(),passwordval.getText().toString());
                                              if(TextUtils.isEmpty(user.getEmail()))
                                              {
                                                  showToast("User not found", Color.BLACK);
                                              }
                                              else{
                                                  Intent intent = new Intent(MainActivity.this, SongIndexActivity.class);
                                                  startActivity(intent);
                                              }

                                          }

                                      }

                                  }
        );


    }
    private void showToast(String messageVal,int color)
    {
        Toast toastVar = Toast.makeText(getApplicationContext(), messageVal, Toast.LENGTH_LONG);
        toastVar.getView().setBackgroundColor(Color.parseColor("#ffeead"));
        TextView messageView = toastVar.getView().findViewById(android.R.id.message);
        messageView.setTextColor(color);
        toastVar.show();
    }

    private User fetchUser(String email,String password)
    {
        User userPojo = null;
        String PATH = "/data/data/com.app.jane/databases/CHRISTMAS_CAROLS";
        SQLiteDatabase dataBase = SQLiteDatabase.openDatabase(PATH,null,SQLiteDatabase.OPEN_READONLY);
        String[] args = {email,password};
        Cursor cursor = dataBase.rawQuery("SELECT * FROM USERS WHERE EMAIL =? AND PASSWORD = ?",args);

        if(null!=cursor)
        {
            userPojo = new User();
            userPojo .setFirstNmae(cursor.getColumnName(cursor.getColumnIndex("first_name")));
            userPojo .setLastName(cursor.getColumnName(cursor.getColumnIndex("last_name")));
            userPojo .setEmail(cursor.getColumnName(cursor.getColumnIndex("email")));
            userPojo .setPassword(cursor.getColumnName(cursor.getColumnIndex("password")));
        }

        return userPojo;

    }
}
