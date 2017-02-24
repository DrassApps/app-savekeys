package com.drassapps.misscode;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class VerRegistro extends Activity {

    private TextView titulo, web, pass, email, notas, categoria;
    private  String titulo_save, web_save, pass_save, email_save, notas_save, categoria_save;
    private Button aceptar;

    private SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.lay_veregistro);

        aceptar = (Button) findViewById(R.id.ver_aceptar);

        titulo = (TextView) findViewById(R.id.ver_text_1);
        web = (TextView) findViewById(R.id.ver_text_2);
        email = (TextView) findViewById(R.id.ver_text_3);
        pass = (TextView) findViewById(R.id.ver_text_4);
        notas = (TextView) findViewById(R.id.ver_text_5);
        categoria = (TextView) findViewById(R.id.ver_text_6);

        prefs = getSharedPreferences(MyPREFERENCES, this.MODE_PRIVATE);

        titulo_save = prefs.getString("titulo_save1", "");
        web_save = prefs.getString("web_save1", "");
        email_save = prefs.getString("email_save1", "");
        pass_save = prefs.getString("pass_save1", "");
        notas_save = prefs.getString("noas_save1", "");
        categoria_save = prefs.getString("categoria_save1", "");

        titulo.setText(titulo_save);
        web.setText(web_save);
        email.setText(email_save);
        pass.setText(pass_save);
        notas.setText(notas_save);
        categoria.setText(categoria_save);


        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VerRegistro.this, HomeScreen.class);
                startActivity(i);
                overridePendingTransition(R.anim.zoom_foward_in, R.anim.zoom_foward_out);
                finish();
            }
        });


    }

}
