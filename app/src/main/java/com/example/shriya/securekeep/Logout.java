package com.example.shriya.securekeep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by shriya on 20/8/15.
 */
public class Logout extends Activity {
    LoginDataBaseAdapter loginDataBaseAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();
        loginDataBaseAdapter.logout();
        loginDataBaseAdapter.close();
        Intent i = new Intent(getApplicationContext(),loginDB.class);
        finish();
        startActivity(i);

    }
}
