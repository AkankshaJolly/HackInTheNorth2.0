package com.hint.paranoid.aadharudhaar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.id.list;

public class LendActivity extends AppCompatActivity  {
    ListView lview;
    Activity context;
    ListViewAdapter lviewAdapter = null;
    ArrayList<RowData> arrayList = new ArrayList<RowData>();
    SQLiteDatabase mydatabase;
    Cursor resultSet;
   /* private final static String month[] = {"January","February","March","April","May",
            "June","July","August","September","October","November","December"};

    private final static String number[] = {"Month - 1", "Month - 2","Month - 3",
            "Month - 4","Month - 5","Month - 6",
            "Month - 7","Month - 8","Month - 9",
            "Month - 10","Month - 11","Month - 12"};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LendActivity.this, AddLendActivity.class);
                startActivity(intent);
            }
        });
        DBConnect();
        createList();

        lview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent,
                                    View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        arrayList.get(position).nameLend, Toast.LENGTH_LONG).show();
            }
        });


    }
    private void DBConnect() {
        mydatabase = openOrCreateDatabase("MoneyDB",MODE_PRIVATE,null);
    }

    private void createList() {
        try {
            resultSet = mydatabase.rawQuery("SELECT * FROM lend;", null);
            if(resultSet.moveToFirst())
            {
                do{
                    int pid = resultSet.getInt(0);
                    String name = resultSet.getString(1);
                    String amt = resultSet.getString(3);
                    String date = resultSet.getString(5);
                    arrayList.add(new RowData(name,amt,date));
                }while(resultSet.moveToNext());
                lview = (ListView) findViewById(R.id.lendList);
                ListViewAdapter adapter = new ListViewAdapter(this, arrayList);
                lview.setAdapter(adapter);
            }
        }
        catch(SQLException e)
        {
            Toast.makeText(context, "failed!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}
