package com.example.haotian.skykeep50;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**Author:HaotianXue u5689296**/

public class NoteEditor extends AppCompatActivity {

    EditText editText_enter;
    DatabaseOperations db;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        editText_enter = (EditText) findViewById(R.id.editText_enter);
        db = new DatabaseOperations(this);
        Intent intent = getIntent();
    }

    public void create(View view){
        sqLiteDatabase = db.getWritableDatabase();
        editText_enter = (EditText)findViewById(R.id.editText_enter);
        String text = editText_enter.getText().toString();
        if(!text.isEmpty()&&text!=null) {
            db.putInformation(sqLiteDatabase, editText_enter.getText().toString());
            Toast.makeText(NoteEditor.this, "Note created", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(NoteEditor.this,"Note can not be empty",Toast.LENGTH_LONG).show();
        }
    }
}
