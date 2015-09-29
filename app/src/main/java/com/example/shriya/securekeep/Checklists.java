package com.example.shriya.securekeep;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;


public class Checklists extends ActionBarActivity {
    //First We Declare Titles And Icons For Our Navigation Drawer List View
    //This Icons And Titles Are holded in an Array as you can see
    LoginDataBaseAdapter loginDataBaseAdapter;
    private int toggleLock = 0;
    String TITLES[] = {"Notes","Checklists","Profile","Logout","Trash"};
    int ICONS[] = {R.drawable.note,R.drawable.checklist,R.drawable.user,R.drawable.logout,R.drawable.trash};

    //Similarly we Create a String Resource for the name and email in the header view
    //And we also create a int resource for profile picture in the header view

    String NAME = "Hafeeza Kuljar";
    String EMAIL = "hafeezakuljar95@gmail.com";
    int PROFILE = R.drawable.me1;

    private Toolbar toolbar;                              // Declaring the Toolbar Object

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    RelativeLayout layout;
    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle

    //ENCRYPT/DECRYPT INFO
    String cipherkey2;
    byte[] cipherkey1;
    byte[] notes;

    //length of cipherkey
    int clen;

    String ini = "abcdefghijklmnopqrstuvwxyz";
    char[] alph = ini.toCharArray();
    char[] ciphernote;

    //Button stuff in xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist);
        layout = (RelativeLayout)findViewById(R.id.layout1);
        Button delnote1 = (Button) findViewById(R.id.delnote);
        final Button addlist = (Button) findViewById(R.id.newlist);
        Button savenote1 = (Button) findViewById(R.id.savenote);
        Button viewnote1 = (Button) findViewById(R.id.viewnote);
        final EditText notetitle1 = (EditText)findViewById(R.id.notetitle);

        delnote1.setOnClickListener(new View.OnClickListener() {

            //hide cryptnote

            @Override
            public void onClick(View v) {


            }

        });

        viewnote1.setOnClickListener(new View.OnClickListener() {

            //hide cryptnote

            @Override
            public void onClick(View v) {

//                Intent i = new Intent(Notes.this, ViewNotes.class);
//                startActivity(i);

            }

        });


        addlist.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
//                EditText newlist = new EditText(getApplicationContext());
//                Toast.makeText(Checklists.this,"Enter an item!",Toast.LENGTH_LONG);
//                newlist.findFocus();
//

                AlertDialog.Builder alert = new AlertDialog.Builder(Checklists.this);
                LayoutInflater inflater = Checklists.this.getLayoutInflater();
                //this is what I did to added the layout to the alert dialog
                final View layout=inflater.inflate(R.layout.dialog,null);
                alert.setView(layout);
                final EditText checkitem1=(EditText)layout.findViewById(R.id.checkitem);

                    alert.setTitle("Enter Checklist Item")
                        .setIcon(R.drawable.pencil)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout1);
                                CheckBox chkTeamName = new CheckBox(getApplicationContext());
                                chkTeamName.setText(checkitem1.getText());
                                chkTeamName.setTextColor(Color.BLACK);
                                layout.addView(chkTeamName);

                            }})
                        .setNegativeButton(android.R.string.no, null).show();





            }

        });
        savenote1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // create a File object for the parent directory

            }

        });



    /* Assinging the toolbar object ot the view
    and setting the the Action bar to our toolbar
     */
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notes");
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        mAdapter = new MyAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.openDrawer,R.string.closeDrawer){




            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }



        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void decrypt()
    {
//        cipherkey2 = ((EditText) findViewById(R.id.cipherkey)).getText().toString();
//        cipherkey1 = ((EditText) findViewById(R.id.cipherkey)).getText().toString().getBytes();
//        final EditText noteText = (EditText) findViewById(R.id.editText);
//        notes = noteText.getText().toString().getBytes();
//        Log.w("","I'm in decrypt");
//        //length of cipherkey
//        clen = cipherkey2.length();
//        ciphernote = new char[notes.length];
//        //start encryption
//        toggleLock = 0;
//        noteText.setBackgroundColor(Color.parseColor("#ffffff"));
//        noteText.setCursorVisible(true);
//        //encrypt
//        Button cryptnote1 = (Button) findViewById(R.id.cryptnote);
//        cryptnote1.setBackground(getResources().getDrawable(R.drawable.unlock));
//
//        if (cipherkey2.equals(""))
//            Toast.makeText(Notes.this, "Enter CipherKey", Toast.LENGTH_SHORT).show();
//        else {
//
//            for (int i = 0, j = 0; i < notes.length; i++, j++) {
//                if (notes[i] == ' ') {
//                    ciphernote[i] = ' ';
//                    continue;
//
//                }
//                if (j >= (clen))
//                    j = 0;
//
//                //int ind = Math.abs((ini.indexOf(notes[i]) + ini.indexOf(cipherkey1[j])) % 26);
//                //  Log.w("", "index: " + String.valueOf(ind) + " i: " + i + " j: " + j);
//                // Toast.makeText(Notes.this,"old: "+notes[i],Toast.LENGTH_SHORT).show();
////                        if(index>25)
//                ciphernote[i] = (char) (notes[i] ^ cipherkey1[j]);
//
//                Log.w("", String.valueOf(ciphernote[i]));
//                //  else
//                //Log.w("", "ini.charAt(index): " + ini.charAt(ind));
//
//                //Toast.makeText(Notes.this,"new: "+notes[i],Toast.LENGTH_SHORT).show();
//
//            }//for
//            String ciphernote1 = new String(ciphernote);
//            //Toast.makeText(Notes.this,"full note: "+ciphernote1,Toast.LENGTH_LONG).show();
//            noteText.setHint("Encrypted Message");
//            noteText.setText(ciphernote1);
//            Log.w("", "plaintext in decrypt: " + ciphernote1);
//            Toast.makeText(this, "Your message has been decrypted!", Toast.LENGTH_SHORT);
//        }
}
    //encryption done

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void encrypt(){
//        toggleLock = 1;
//        cipherkey2 = ((EditText) findViewById(R.id.cipherkey)).getText().toString();
//        cipherkey1 = ((EditText) findViewById(R.id.cipherkey)).getText().toString().getBytes();
//        Log.w("","Cipherkey: "+cipherkey2+"Booyah");
//        if(cipherkey2.equals(""))
//        {   Log.w("","Cipherkey: "+"NoBooyah");
//            Toast.makeText(this,"Enter cipherkey!",Toast.LENGTH_LONG);
//        }
//        else {
//            Button cryptnote1 = (Button) findViewById(R.id.cryptnote);
//            final EditText noteText = (EditText) findViewById(R.id.editText);
//            cryptnote1.setBackground(getResources().getDrawable(R.drawable.lock));
//            noteText.setBackgroundColor(Color.parseColor("#000000"));
//            noteText.setCursorVisible(false);
//            notes = noteText.getText().toString().getBytes();
//            clen = cipherkey2.length();
//            ciphernote = new char[notes.length];
//
//            for (int i = 0, j = 0; i < notes.length; i++, j++) {
//                if (notes[i] == ' ') {
//                    ciphernote[i] = ' ';
//                    continue;
//
//                }
//                if (j >= (clen))
//                    j = 0;
//                ciphernote[i] = (char) (notes[i] ^ cipherkey1[j]);
//            }//for
//            String ciphernote1 = new String(ciphernote);
//            //Toast.makeText(Notes.this,"full note: "+ciphernote1,Toast.LENGTH_LONG).show();
//            Log.w("", "ciphertext in encrypt: " + ciphernote1);
//            noteText.setText(ciphernote1);
//            Toast.makeText(this, "Your message has been encrypted!", Toast.LENGTH_SHORT);
//        }

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