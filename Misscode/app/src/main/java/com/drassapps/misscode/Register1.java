package com.drassapps.misscode;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Register1 extends Activity {

    private EditText email;
    private TextView hint, legal, siguiente;
    private ImageButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.lay_register1);

        email = (EditText) findViewById(R.id.email_v0);
        hint = (TextView) findViewById(R.id.text_hint);
        legal = (TextView) findViewById(R.id.legal);
        siguiente = (TextView) findViewById(R.id.text_siguiente);
        next = (ImageButton) findViewById(R.id.next);

        hint.setVisibility(View.INVISIBLE);
        hint.setTextColor(getResources().getColor(R.color.orange));

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setHint("");
                email.setHintTextColor(getResources().getColor(R.color.orange));
                email.setBackground(getResources().getDrawable(R.drawable.back_edit_selected));
                hint.setVisibility(View.VISIBLE);
            }
        });

        legal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register1.this, Legal.class);
                startActivity(i);
                overridePendingTransition(R.anim.zoom_foward_in, R.anim.zoom_foward_out);
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String check_email = email.getText().toString();
                String a = "@";

                if (check_email.contains(a) && check_email.length() > 4 && !check_email.contains
                        (" ") && !check_email.contains("-")){

                    Intent i = new Intent(Register1.this, Register2.class);
                    i.putExtra("email",email.getText().toString());
                    startActivity(i);
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    finish();

                }else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Register1.this);

                    builder.setMessage("Introduce una dirección de email válida.")
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
                }


            }
        });

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String check_email = email.getText().toString();
                String a = "@";

                if (check_email.contains(a) && check_email.length() > 4 && !check_email.contains
                        (" ") && !check_email.contains("-")){

                    Intent i = new Intent(Register1.this, Register2.class);
                    i.putExtra("email",email.getText().toString());
                    startActivity(i);
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    finish();

                }else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Register1.this);

                    builder.setMessage("Introduce una dirección de email válida.")
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
                }
            }
        });

    }
}
