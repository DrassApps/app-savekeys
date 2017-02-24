package com.drassapps.misscode;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Legal2 extends Activity {

    private ImageButton volver;
    String email_r1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.lay_legal_2);

        email_r1 = getIntent().getExtras().getString("email");

        volver = (ImageButton) findViewById(R.id.back_legal_2);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Legal2.this, Register2.class);
                i.putExtra("email",email_r1);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                finish();
            }
        });


    }
}