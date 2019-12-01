package com.app.jane;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CreateUser extends AppCompatActivity {

    Button btnReg;
    EditText edtFirst,edtLast,  edtPass, edtConfPass,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user_login);
        edtFirst=(EditText)findViewById(R.id.edtfirstname);
        edtFirst.setSelected(true);
        edtLast=(EditText)findViewById(R.id.edtlastname);
        edtPass=(EditText)findViewById(R.id.edtPass);
        edtConfPass=(EditText)findViewById(R.id.edtConfirmPass);
        //Initialization of Register Button
        btnReg=(Button)findViewById(R.id.button1);
        email = (EditText) findViewById(R.id.edtUsername);

        // Validation done here on button click

        ContentValues contentValues = new ContentValues();
       /* contentValues.put(Data);
        insertUser()
        */

        btnReg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View argso) {
                String PATH = "/data/data/com.app.jane/databases/CHRISTMAS_CAROLS";
                SQLiteDatabase dataBase = SQLiteDatabase.openDatabase(PATH,null,SQLiteDatabase.OPEN_READWRITE);
                SQLiteStatement statement = dataBase.compileStatement("INSERT INTO USERS VALUES(?,?,?,?)");
                statement.bindString(1,edtFirst.getText().toString());
                statement.bindString(2,edtLast.getText().toString());
                statement.bindString(3,edtPass.getText().toString());
                statement.bindString(4,email.getText().toString());
                statement.execute();

                Intent intent = new Intent(CreateUser.this, MainActivity.class);
                startActivity(intent);
            }

             }
        );
    }

}
