package com.example.shriya.securekeep;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static android.text.InputType.*;

/**
 * Created by shriya on 20/8/15.
 */
public class ViewNotes extends ActionBarActivity {
    //First We Declare Titles And Icons For Our Navigation Drawer List View
    //This Icons And Titles Are holded in an Array as you can see
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

    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle

    ArrayAdapter<String> adapter = null;


    ArrayList<String> FilesInFolder = new ArrayList<String>();
    String selectedFromList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewnotes);

        //list adapter
        final ListView listview = (ListView) findViewById(R.id.listview);


        final ArrayList<String> list = new ArrayList<String>();

        FilesInFolder = GetFiles("/sdcard/SecureKeep/Shriya/Notes/");
        if (FilesInFolder != null) {
            for (int i = 0; i < FilesInFolder.size(); ++i) {
                list.add(FilesInFolder.get(i));
            }
            adapter = new ArrayAdapter(ViewNotes.this,
                    android.R.layout.simple_list_item_1, list);
            listview.setAdapter(adapter);
            registerForContextMenu(listview);
        }

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
/* Assinging the toolbar object ot the view
    and setting the the Action bar to our toolbar
     */
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Notes");



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


    public ArrayList<String> GetFiles(String DirectoryPath) {
        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);

        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0)
            return null;
        else {
            for (int i = 0; i < files.length; i++)
                MyFiles.add(files[i].getName());
        }//else

        return MyFiles;
    }



    public void refresh() {
        //refresh List
        FilesInFolder = GetFiles("/sdcard/SecureKeep/Shriya/Notes/");
        Log.w("app", "In refresh");
        final ListView listview = (ListView) findViewById(R.id.listview);


        final ArrayList<String> list = new ArrayList<String>();

        if (FilesInFolder != null) {
            Log.w("app", "Files in folder not null");
            for (int i = 0; i < FilesInFolder.size(); ++i) {
                list.add(FilesInFolder.get(i));
            }
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
        registerForContextMenu(listview);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // Get the info on which item was selected
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        // Get the Adapter behind your ListView (this assumes you're using
        // a ListActivity; if you're not, you'll have to store the Adapter yourself
        // in some way that can be accessed here.)
        // Retrieve the item that was clicked on
        selectedFromList = adapter.getItem(info.position);
        menu.setHeaderTitle(selectedFromList);
        menu.add(0, v.getId(), 0, "View");
        menu.add(0, v.getId(), 0, "Delete");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(this,"clicked "+item.getTitle(),Toast.LENGTH_LONG);
        if (item.getTitle() == "View") {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //you should edit this to fit your needs
                builder.setTitle("Double Edit Text");

                final EditText one = new EditText(this);
                one.setHint("username");//optional
                final EditText two = new EditText(this);
                two.setHint("password");//optional

                //in my example i use TYPE_CLASS_NUMBER for input only numbers
                one.setInputType(TYPE_CLASS_TEXT);
                two.setInputType(TYPE_CLASS_TEXT);

                LinearLayout lay = new LinearLayout(this);
                lay.setOrientation(LinearLayout.VERTICAL);
                lay.addView(one);
                lay.addView(two);
                builder.setView(lay);

                // Set up the buttons
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //get the two inputs
                        String u = one.getText().toString();
                        String p = two.getText().toString();
                        Log.w("",u+p);
                        if (u.equals("feeza") && p.equals("feeza")) {
                            Intent i = new Intent(ViewNotes.this, ViewNote.class);
                            i.putExtra("filename", selectedFromList);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Username/Password incorrect!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                builder.show();



        } else if (item.getTitle() == "Delete") {
               new AlertDialog.Builder(this)
                    .setTitle("Delete File")
                    .setMessage("Do you really want to delete "+selectedFromList+"?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SecureKeep/Shriya/Notes";
                            path+="/"+selectedFromList;
                            File file = new File(path);
                            file.delete();
                            refresh();
                            Toast.makeText(ViewNotes.this,"Deleted "+selectedFromList,Toast.LENGTH_SHORT);
                        }})
                    .setNegativeButton(android.R.string.no, null).show();

        } else {
            return false;
        }
        return true;
    }
}
