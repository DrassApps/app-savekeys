package com.drassapps.misscode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TwoFragment extends Fragment {

    // WEB

    private Button bt;
    private ImageView smile;
    private TextView text;

    public TwoFragment() {
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

        View view=inflater.inflate(R.layout.fragment_two, container, false);

        bt = (Button) view.findViewById(R.id.button_f2);
        smile = (ImageView) view.findViewById(R.id.smile_f2);
        text = (TextView) view.findViewById(R.id.text_f2);


        bt.setVisibility(View.INVISIBLE);

        if (clase.equals("Personal") || clase.equals("Favorito")){

            bt.setVisibility(View.INVISIBLE);
            smile.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);

        }else if (clase.equals("Web")) {

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
