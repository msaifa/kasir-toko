package com.komputerkit.kasirtoko.Fragment.Master;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

public class MMutilitas extends Fragment {

    Utilitas utilitas;
    View v;
    ConstraintLayout btnSatu,btnDua,btnTiga,btnEmpat;

    @Override
    public void onStart() {
        super.onStart();
        utilitas = new Utilitas(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_utilitas,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view;
        init();
        event();
    }

    public void init() {
        btnSatu = v.findViewById(R.id.mUbackupdata);
        btnDua = v.findViewById(R.id.mUloaddata);
        btnTiga = v.findViewById(R.id.mUresetdata);
        btnEmpat = v.findViewById(R.id.mUbelifitur);
    }

    public void event() {
        btnSatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnBackup));
            }
        });
        btnDua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnRestore));
            }
        });
        btnTiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnReset));
            }
        });
        btnEmpat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnBeliFitur));
            }
        });
    }

}
