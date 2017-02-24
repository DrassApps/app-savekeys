package com.drassapps.misscode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OneFragment extends Fragment {

    // PERSONAL

    private Button bt;

    private ImageView smile;
    private TextView text;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        HomeScreen activity = (HomeScreen) getActivity();
        String clase = activity.clase();

        View view=inflater.inflate(R.layout.fragment_one, container, false);

        bt = (Button) view.findViewById(R.id.button_f1);
        smile = (ImageView) view.findViewById(R.id.smile_f1);
        text = (TextView) view.findViewById(R.id.text_f1);

        bt.setVisibility(View.INVISIBLE);

        if (clase.equals("Web") || clase.equals("Favorito")){

            bt.setVisibility(View.INVISIBLE);
            smile.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);

        }else if (clase.equals("Personal")){

            bt.setVisibility(View.VISIBLE);
            smile.setVisibility(View.INVISIBLE);
            text.setVisibility(View.INVISIBLE);

        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent (getActivity(), VerRegistro.class);
                startActivity(i1);
            }
        });

        return view;




    }


}
