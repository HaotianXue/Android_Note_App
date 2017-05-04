package com.example.haotian.skykeep50;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.*;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.widget.ShareDialog;
import java.util.Arrays;
import java.util.List;


/**Author:HaotianXue u5689296**/

public class ViewRecord extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    DatabaseOperations db;
    EditText editText_update;
    listDataAdapter listDataAdapter;
    MainActivity mainActivity;
    Cursor cursor;
    Note note;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    ShareDialog shareDialog;
    ImageView imageView;
    VideoView videoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        editText_update = (EditText) findViewById(R.id.editText_update);
        editText_update.setText(i.getExtras().getString("Note"));
        listDataAdapter = new listDataAdapter(getApplicationContext(),R.layout.row_layout);
        db = new DatabaseOperations(getApplicationContext());
        sqLiteDatabase = db.getWritableDatabase();
        cursor = db.getInformation(sqLiteDatabase);
        if(cursor.moveToFirst()){
            do{
                String content;
                content = cursor.getString(1);
//                Log.e("Hello",content);
                int newID = cursor.getInt(cursor.getColumnIndexOrThrow(TableData.TableInfo.ID));
                note = new Note(content,newID);
                listDataAdapter.add(note);

            }while (cursor.moveToNext());
        }
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
//        listDataAdapter = new listDataAdapter(getApplicationContext(),R.layout.row_layout);
    }

    public void delete(View view){
        db = new DatabaseOperations(getApplicationContext());
        sqLiteDatabase = db.getWritableDatabase();
        Intent i = getIntent();
        int position = i.getExtras().getInt("id");
        Note note = (Note)listDataAdapter.getItem(position);
        db.deleteInformation(note.getNotID(), sqLiteDatabase);
        listDataAdapter.remove(note);
        listDataAdapter.notifyDataSetChanged();
        Toast.makeText(ViewRecord.this,"Item deleted",Toast.LENGTH_LONG).show();
//        listDataAdapter.notifyDataSetChanged();
//        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_viewrecord, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action__save:
                updateData();
                break;
            case R.id.action_share:
                shareonFacebook();
                break;
            case R.id.action_fontchange:
                break;
        }

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action__save) {
//            updateData();
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void updateData(){
        db = new DatabaseOperations(getApplicationContext());
        sqLiteDatabase = db.getWritableDatabase();
        Intent i = getIntent();
        int position = i.getExtras().getInt("id");
        Note note = (Note)listDataAdapter.getItem(position);
        String content = editText_update.getText().toString();
        if(!content.isEmpty()&&content!=null){
        note.setContent(content);
        db.updateInformation(content,note.getNotID(),sqLiteDatabase);
        listDataAdapter.update(position,note);
        listDataAdapter.notifyDataSetChanged();
        Toast.makeText(ViewRecord.this,"Note updated",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(ViewRecord.this,"Note can not be empty",Toast.LENGTH_LONG).show();
        }
    }

    public void shareonFacebook(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        List<String> permissonNeeds = Arrays.asList("publish_actions");
        loginManager = LoginManager.getInstance();
        loginManager.logInWithPublishPermissions(this,permissonNeeds);
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {
                System.out.println("onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                System.out.println("onError");
            }
        });


    }



//    private void displayData(){
//        db = new DatabaseOperations(getApplicationContext());
//        sqLiteDatabase = db.getWritableDatabase();
//        String sql = "SELECT * FROM "+ TableData.TableInfo.TABLE_NAME+" WHERE "+ TableData.TableInfo._ID+" = "+listDataAdapter.getItem()
//    }

}
