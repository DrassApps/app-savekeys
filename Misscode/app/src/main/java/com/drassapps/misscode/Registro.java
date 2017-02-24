package com.drassapps.misscode;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Registro extends Activity {

    private EditText titulo, web, email, pass, notas;
    private Spinner categoria;
    private Button aceptar;

    private String categoria_elegida;

    private SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";

    public static final String titulo_save1 = "titulo_save1";
    public static final String web_save1 = "web_save1";
    public static final String email_save1 = "email_save1";
    public static final String pass_save1 = "pass_save1";
    public static final String notas_save1 = "notas_save1";
    public static final String categoria_save1 = "categoria_save1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.registro_pass);

        prefs = getSharedPreferences(MyPREFERENCES, this.MODE_PRIVATE);


        titulo = (EditText) findViewById(R.id.registro_text_1);
        web = (EditText) findViewById(R.id.registro_text_2);
        email = (EditText) findViewById(R.id.registro_text_3);
        pass = (EditText) findViewById(R.id.registro_text_4);
        notas = (EditText) findViewById(R.id.registro_text_5);

        categoria = (Spinner) findViewById(R.id.registro_spinner);
        aceptar = (Button) findViewById(R.id.registro_aceptar);

        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pass.setBackground(getResources().getDrawable(R.drawable.back_edit_selected));
            }
        });

        titulo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                titulo.setBackground(getResources().getDrawable(R.drawable.back_edit_selected));
            }
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                email.setBackground(getResources().getDrawable(R.drawable.back_edit_selected));
            }
        });

        web.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                web.setBackground(getResources().getDrawable(R.drawable.back_edit_selected));
            }
        });

        notas.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                notas.setBackground(getResources().getDrawable(R.drawable.back_edit_selected));
            }
        });

        List<String> categories = new ArrayList<String>();
        categories.add("Favorito");
        categories.add("Personal");
        categories.add("Web");


        categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                categoria_elegida = item;
                // Showing selected spinner item
                // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        categoria.setAdapter(dataAdapter);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = prefs.edit();

                String tit = titulo.getText().toString() ;
                editor.putString(titulo_save1, tit);
                editor.commit();

                String pasw = pass.getText().toString();
                editor.putString(pass_save1, pasw);
                editor.commit();

                String ema = email.getText().toString();
                editor.putString(email_save1, ema);
                editor.commit();

                String weba = web.getText().toString();
                editor.putString(web_save1, weba);
                editor.commit();

                String not = notas.getText().toString();
                editor.putString(notas_save1, not);
                editor.commit();

                editor.putString(categoria_save1, categoria_elegida);
                editor.commit();

                Intent i = new Intent(Registro.this, HomeScreen.class);
                startActivity(i);
                overridePendingTransition(R.anim.zoom_foward_in, R.anim.zoom_foward_out);
                finish();

            }
        });



    }

}
