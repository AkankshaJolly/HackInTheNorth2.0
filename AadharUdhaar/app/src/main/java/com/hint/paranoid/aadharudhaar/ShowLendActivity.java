package com.hint.paranoid.aadharudhaar;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShowLendActivity extends AppCompatActivity {
    private int position;
    SQLiteDatabase mydatabase;
    Cursor resultSet;
    TextView nametv,amttv,phonetv,interesttv,uidtv,addrtv,statetv,commenttv,pintv,datetv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        loadTextViews();
        position = Integer.parseInt(getIntent().getExtras().getString("position"))+1;
        DBConnect();
        displayData();


    }
    private void loadTextViews()
    {
        nametv = (TextView) findViewById(R.id.lend_name);
        phonetv = (TextView) findViewById(R.id.lend_phone);
        amttv = (TextView) findViewById(R.id.amt_lend);
        interesttv = (TextView) findViewById(R.id.interest_lend);
        commenttv = (TextView) findViewById(R.id.comments_lend);
        uidtv = (TextView) findViewById(R.id.uid);
        addrtv = (TextView) findViewById(R.id.address);
        statetv = (TextView) findViewById(R.id.state);
        pintv = (TextView) findViewById(R.id.postal);
        datetv = (TextView) findViewById(R.id.date_lend);
    }

    private void displayData() {
        try{
            resultSet = mydatabase.rawQuery("SELECT * FROM lend WHERE id = "+position+ ";", null);
            resultSet.moveToFirst();
            //Toast.makeText(this, Integer.toString(row_num), Toast.LENGTH_SHORT).show();
            String name = resultSet.getString(1);
            String phone = resultSet.getString(2);
            String  amount = Integer.toString(resultSet.getInt(3));
            String interest = Integer.toString(resultSet.getInt(4));
            String date = resultSet.getString(5);
            String comments = resultSet.getString(9);
            String uid = resultSet.getString(10);
            String address = resultSet.getString(11);
            String state = resultSet.getString(12);
            String pin = Integer.toString(resultSet.getInt(13));
            nametv.setText(name);
            phonetv.setText(phone);
            amttv.setText(amount);
            interesttv.setText(interest);
            commenttv.setText(comments);
            datetv.setText(date);
            uidtv.setText(uid);
            addrtv.setText(address);
            statetv.setText(state);
            pintv.setText(pin);
        } catch(SQLException e)
        {
            Toast.makeText(this, "failed to display.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void DBConnect() {
        mydatabase = openOrCreateDatabase("MoneyDB",MODE_PRIVATE,null);
    }

}
