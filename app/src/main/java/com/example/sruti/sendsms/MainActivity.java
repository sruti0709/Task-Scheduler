package com.example.sruti.sendsms;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.AppCompatEditText;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> Name = new ArrayList<String>();
    ArrayList<String> Phone = new ArrayList<String>();
    ArrayList<String> Work = new ArrayList<String>();
    Button btnWork, btnMate;
    EditText editTextPhone,editTextWork, editTextName;
    String NAMEFILE,PHONEFILE, WORKFILE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextWork = (EditText) findViewById(R.id.editTextWork);
        btnMate = (Button) findViewById(R.id.btnMate);
        btnWork = (Button) findViewById(R.id.btnWork);
        NAMEFILE = "N7";
        PHONEFILE = "P7";
        WORKFILE = "W7";

        //sendMessage();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      .setAction("Action", null).show();
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                try{
                    FileInputStream fin = openFileInput(NAMEFILE);
                    FileInputStream finPh = openFileInput(PHONEFILE);
                    FileInputStream finWrk = openFileInput(WORKFILE);

                    int c;
                    String name="", phone ="",worktemp = "";

                    while( (c = fin.read()) != -1){
                           name = name + Character.toString((char) c);
                    }
                   // Name.add(name);
                    String[] abc;
                    abc = name.split(";");
                    for(int i=0;i<abc.length;i++)
                        Name.add(abc[i]);
                   // Toast.makeText(getBaseContext(),Name.get(0).toString(),Toast.LENGTH_SHORT).show();

                    while( (c = finPh.read()) != -1){
                        phone = phone + Character.toString((char) c);
                    }
                    //Phone.add(phone);
                    //Toast.makeText(getBaseContext(),Phone.get(0).toString(),Toast.LENGTH_SHORT).show();
                    abc = phone.split(";");
                    for(int i=0;i<abc.length;i++)
                        Phone.add(abc[i]);

                    while( (c = finWrk.read()) != -1){
                        worktemp = worktemp + Character.toString((char) c);
                    }
                    abc = worktemp.split(";");
                    for(int i=0;i<abc.length;i++)
                        Work.add(abc[i]);
                    if(day == 7)
                    {
                        Collections.rotate(Name,1);
                        Collections.rotate(Phone,1);
                    }
                }
                catch(Exception e){
                }
                       ComputeTask();
            }
        });

        btnMate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                try {
                    FileOutputStream fOut = openFileOutput(NAMEFILE,Context.MODE_APPEND);
                    fOut.write(editTextName.getText().toString().getBytes());
                    fOut.write(";".getBytes());
                    fOut.flush();
                    fOut.close();
                    FileOutputStream fOutPh = openFileOutput(PHONEFILE,Context.MODE_APPEND);
                    fOutPh.write(editTextPhone.getText().toString().getBytes());
                    fOutPh.write(";".getBytes());
                    fOutPh.flush();
                    fOutPh.close();
                }

                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

                Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_LONG).show();
                //String str = Phone.get(0).toString();
               // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                //sendMessage();
            }
        });

        btnWork.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

//                if (editTextWork.getText().toString().length() == 0)
//                    editTextWork.setError("Work is required!");
//                else
                   // Work.add(editTextWork.getText().toString());
                try {
                    FileOutputStream fOutWrk = openFileOutput(WORKFILE, Context.MODE_APPEND);
                    fOutWrk.write(editTextWork.getText().toString().getBytes());
                    fOutWrk.write(";".getBytes());
                    fOutWrk.flush();
                    fOutWrk.close();
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }


                Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void ComputeTask(){
        int numberOfMembers = Name.size();
        int numberOfWork = Work.size();
       // ArrayList<String>[][] schedule = new ArrayList[numberOfMembers][];
        String[][] schedule = new String[numberOfMembers][100];
        String[] myWorks = new String[numberOfMembers];
        int k = 0;
        for(int i=0, j=0; i<numberOfWork; i++) {
            //int n = schedule[j].length;
            String work = Work.get(i).toString();
            //schedule[j][0].add(work);
            schedule[j][k]=work;
            if (k==0)
                myWorks[j]= work;
            else
                myWorks[j]+= "; " + work;

            if(j==(numberOfMembers-1)) {
                j = 0;
                k++;
            }
            else
                j++;
        }
        //Toast.makeText(getApplicationContext(), schedule[0][0].toString(), Toast.LENGTH_LONG).show();

//        sendMessage();
//    }
//
//    protected void sendMessage() {
        //Log.i("Send SMS", "");
        //String phoneNo = txtphoneNo.getText().toString();
        //String message = txtMessage.getText().toString();
//        int numberOfMembers = Name.size();
     //   String[] myWorks = new String[numberOfMembers];
        //myWorks[0].append("");
        for(int i=0;i<numberOfMembers;i++)
        try {
            SmsManager smsManager = SmsManager.getDefault();

            String str = "SMS sent to : "+ Phone.get(i).toString() + " Name: " + Name.get(i).toString() + " Works: " + myWorks[i] + "\n";
                    //schedule[i][0];
            String msgBody = "Dear "+Name.get(i).toString()+", your task for today is to clean "+myWorks[i];
            smsManager.sendTextMessage(Phone.get(i).toString(), null, msgBody, null, null);
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
