package com.drassapps.misscode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String first = "first";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        prefs = getSharedPreferences(MyPREFERENCES, this.MODE_PRIVATE);

        final String n1 = prefs.getString("first", "");

        if (n1.equals("")) {

            Intent i = new Intent(MainActivity.this, Register1.class);
            startActivity(i);
            finish();

        }else {

            Intent i = new Intent(MainActivity.this, LockScreen.class);
            startActivity(i);
            finish();


        }
    }
}
