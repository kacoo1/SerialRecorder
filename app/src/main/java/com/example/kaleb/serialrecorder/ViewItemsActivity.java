package com.example.kaleb.serialrecorder;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewItemsActivity extends AppCompatActivity{

    //declare the views we are going to use
    private ListView itemListView;

    public static ArrayList<String> itemArray = new ArrayList<String>(); //ArrayList that holds our items
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("View Items");
        itemArray.clear(); //clear array each time activity is created so list items don't duplicate.

        dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.getAllItems(); //get all items from our database

        itemListView = (ListView) findViewById(R.id.itemListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, itemArray); //set our item arraylist to an array adapter
        itemListView.setAdapter(adapter); //set adapter to our listview

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent singleItemIntent = new Intent(view.getContext(), SingleItemActivity.class);
                String id = String.valueOf(l + 1);
                singleItemIntent.putExtra("ID", id);
                startActivity(singleItemIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_items_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //to handle the up arrow event
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case (android.R.id.home):
                finish();
                break;
            case(R.id.delete):
                dbHandler.wipeDatabase();
                finish();
                Toast.makeText(ViewItemsActivity.this, "Items Deleted", Toast.LENGTH_LONG).show();
                break;
            case(R.id.help):
                Intent launchHelp = new Intent(this, HelpActivity.class);
                startActivity(launchHelp);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}