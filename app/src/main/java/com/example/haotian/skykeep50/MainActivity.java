package com.example.haotian.skykeep50;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**Author:HaotianXue u5689296**/


public class MainActivity extends AppCompatActivity {

    ListView listView;
    Cursor curosr;
    FloatingActionButton fab;
    DatabaseOperations db;
    SQLiteDatabase sqLiteDatabase;
    Note note;
    listDataAdapter listDataAdapter;
    private CallbackManager callbackManager;
    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        callbackManager = CallbackManager.Factory.create();
//        List<String> permissonNeeds = Arrays.asList("publish_actions");
//        loginManager = LoginManager.getInstance();
//        loginManager.logInWithPublishPermissions(this,permissonNeeds);
//        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//
//            }
//
//            @Override
//            public void onCancel() {
//                System.out.println("onCancel");
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                System.out.println("onError");
//            }
//        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        listView = (ListView) findViewById(R.id.listView);
        registerForContextMenu(listView);
        listDataAdapter = new listDataAdapter(getApplicationContext(),R.layout.row_layout);
        db = new DatabaseOperations(getApplicationContext());
        sqLiteDatabase = db.getWritableDatabase();
        curosr = db.getInformation(sqLiteDatabase);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),ViewRecord.class);
                i.putExtra("Note", listDataAdapter.getItem(position).toString());
                i.putExtra("id",position);
//                id1 = position;
                startActivity(i);
            }
        });
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                removeItemFromList(position);
////                listDataAdapter.notifyDataSetChanged();
//                return true;
//            }
//        });
        if(curosr.moveToFirst()){
            do{
                String content;
                content = curosr.getString(1);
//                Log.e("Hello",content);
                int newID = curosr.getInt(curosr.getColumnIndexOrThrow(TableData.TableInfo.ID));
                note = new Note(content,newID);
                listDataAdapter.add(note);

            }while (curosr.moveToNext());
        }
        listView.setAdapter(listDataAdapter);
        sqLiteDatabase.close();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(),"You select settings option",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void edit(View view){
        Intent intent = new Intent(this,NoteEditor.class);
        startActivity(intent);
    }

    public void removeItemFromList(final int position){
        final int deletePosition = position;
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db = new DatabaseOperations(getApplicationContext());
                sqLiteDatabase = db.getWritableDatabase();
                Note note = (Note)listDataAdapter.getItem(deletePosition);
                db.deleteInformation(note.getNotID(),sqLiteDatabase);
                listDataAdapter.remove(listDataAdapter.getItem(deletePosition));
                listDataAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,"Item deleted",Toast.LENGTH_LONG).show();
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,View view,ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.menu_delete:
                removeItemFromList(info.position);
                listDataAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    private void share(){
        
    }

}
