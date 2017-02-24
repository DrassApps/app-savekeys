package com.drassapps.misscode;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Register2 extends Activity {

    private EditText email, pass;
    private TextView legal, text_hint, finalizar;
    private ImageButton next;

    String email_r1;

    private SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name5 = "first";
    public static final String contra = "pass";
    public static final String email_fin = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.lay_register2);

        prefs = getSharedPreferences(MyPREFERENCES, this.MODE_PRIVATE);

        email_r1 = getIntent().getExtras().getString("email");

        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        email = (EditText) findViewById(R.id.email_register_v0);
        pass = (EditText) findViewById(R.id.pass_v0);
        legal = (TextView) findViewById(R.id.legal_register2);
        finalizar = (TextView) findViewById(R.id.text_finalizar);
        text_hint = (TextView) findViewById(R.id.text_hint2_pass);
        next = (ImageButton) findViewById(R.id.next_register_2);

        email.setText(email_r1);

        legal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register2.this, Legal2.class);
                i.putExtra("email", email_r1);
                startActivity(i);
                overridePendingTransition(R.anim.zoom_foward_in, R.anim.zoom_foward_out);
                finish();
            }
        });

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pass.getText().toString().length() < 3 ) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Register2.this);

                    builder.setMessage("Introduce una contraseña con más de 3 carácteres.")
                            .setTitle("Cuidado")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    builder.setCancelable(false);
                    AlertDialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                } else {

                    SharedPreferences.Editor editor = prefs.edit();
                    String n = "false";
                    editor.putString(Name5, n);
                    editor.commit();

                    String pasw = pass.getText().toString();
                    editor.putString(contra, pasw);
                    editor.commit();

                    String emai = email.getText().toString();
                    editor.putString(email_fin, emai);
                    editor.commit();

                    Intent i = new Intent(Register2.this, HomeScreen.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    finish();

                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: REVISAR

                if (pass.getText().toString().length() < 3 ) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Register2.this);

                    builder.setMessage("Introduce una contraseña con más de 3 carácteres.")
                            .setTitle("Cuidado")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    builder.setCancelable(false);
                    AlertDialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                } else {

                    SharedPreferences.Editor editor = prefs.edit();
                    String n = "false";
                    editor.putString(Name5, n);
                    editor.commit();

                    String pasw = pass.getText().toString();
                    editor.putString(contra, pasw);
                    editor.commit();

                    String emai = email.getText().toString();
                    editor.putString(email_fin, emai);
                    editor.commit();

                    Intent i = new Intent(Register2.this, HomeScreen.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    finish();

                }
            }
        });


        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pass.setBackground(getResources().getDrawable(R.drawable.back_edit_selected));
                text_hint.setVisibility(View.INVISIBLE);
            }
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                email.setBackground(getResources().getDrawable(R.drawable.back_edit_selected));
            }
        });


    }
}
