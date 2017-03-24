package com.hint.paranoid.aadharudhaar;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ParseException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AddUserActivity extends AppCompatActivity {
    SQLiteDatabase mydatabase;
    private EditText name,phone,amt,interest,comment,uid,addr,state,pin,date;
    private String nameString,phoneString,commentString,uidString,addrString,stateString,dateString,amtString,interestString,pinString;
    private int amtInt, interestInt, pinInt;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
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
        createDB();
        //Buttons --------------------------------
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInput();
                saveInput();
            }
        });



    }
    private void createDB()
    {
        mydatabase = openOrCreateDatabase("BorrowDB", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS " +
                "borrow(id INTEGER PRIMARY KEY AUTOINCREMENT,name varchar NOT NULL,phone varchar NOT NULL,amount integer NOT NULL,interest integer,date varchar NOT NULL,comments varchar,uid varchar,address varchar,state varchar,pin integer);");
    }
    private void loadTextViews()
    {
        name = (EditText) findViewById(R.id.borrow_name);
        phone = (EditText) findViewById(R.id.phone);
        amt = (EditText) findViewById(R.id.amt);
        interest = (EditText) findViewById(R.id.interest);
        comment = (EditText) findViewById(R.id.comments);
        uid = (EditText) findViewById(R.id.uid);
        addr = (EditText) findViewById(R.id.address);
        state = (EditText) findViewById(R.id.state);
        pin = (EditText) findViewById(R.id.postal);
        date = (EditText) findViewById(R.id.date);
    }
    private void getInput()
    {
        nameString = name.getText().toString();
        phoneString = phone.getText().toString();
        amtString = amt.getText().toString();
        if(!amtString.equals(""))
        {
            amtInt = Integer.parseInt(amtString);
        }
        interestString = interest.getText().toString();
        if(!interestString.equals(""))
            interestInt = Integer.parseInt(interestString);
        commentString = comment.getText().toString();
        uidString = uid.getText().toString();
        addrString = addr.getText().toString();
        stateString = state.getText().toString();
        pinString = pin.getText().toString();
        if(!pinString.equals(""))
        pinInt = Integer.parseInt(pinString);
        dateString = date.getText().toString();
        //Toast.makeText(this, nameString, Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, Integer.toString(amtInt), Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, dateString, Toast.LENGTH_SHORT).show();
    }
    private void saveInput()
    {
        if(validate())
        {
            try {
                mydatabase.execSQL("INSERT INTO borrow(name,phone,amount,interest,date,comments,uid,address,state,pin) VALUES('"+nameString+"'," +
                        "'"+phoneString+"',"+amtInt+","+interestInt+","+"'"+dateString+"','"+commentString+"','"+uidString+"','"+addrString+"','"+stateString+"',"+pinInt+");");
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
            }catch (SQLException e)
            {
                e.printStackTrace();
                Toast.makeText(this, "database query failed", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            name.setText("");
            phone.setText("");
            amt.setText("");
            interest.setText("");
            comment.setText("");
            uid.setText("");
            addr.setText("");
            state.setText("");
            pin.setText("");
            date.setText("");
            Toast.makeText(this, "Invalid entry", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validate() {

        if(nameString.equals(""))
        {
            //Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
            return false;

        }
        if(phoneString.length()!=10)
        {
            //Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(amtString.equals(""))
        {
            //Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isDateValid(dateString))
        {
            //Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!uidString.equals("") && uidString.length()!=12)
        {
            //Toast.makeText(this, "5", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!pinString.equals("") && pinString.length()!=6)
        {
            //Toast.makeText(this, "6", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }
    public boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
