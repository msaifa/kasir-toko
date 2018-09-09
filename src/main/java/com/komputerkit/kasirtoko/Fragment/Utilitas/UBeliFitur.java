package com.komputerkit.kasirtoko.Fragment.Utilitas;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.komputerkit.kasirtoko.ActivityUtama;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Config;
import com.komputerkit.kasirtoko.Utilitas.InApp;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

/**
 * Created by SAIF on 10/01/2018.
 */

public class UBeliFitur extends Fragment {
    View v ;
    ConstraintLayout wBeli ;
    Utilitas utilitas ;

    @Override
    public void onStart() {
        super.onStart();
        utilitas= new Utilitas(getActivity()) ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_beli_fitur,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;
        init();
        event();
    }

    public void init(){
        wBeli = v.findViewById(R.id.wBeli) ;
    }

    public void event(){
        wBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityUtama)getActivity()).bayar();
            }
        });
    }
}
