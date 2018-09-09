package com.komputerkit.kasirtoko;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.komputerkit.kasirtoko.Utilitas.Utilitas;

public class fragment_main_menu extends Fragment {

    Utilitas utilitas;
    View v;
    ConstraintLayout btnSatu,btnDua,btnTiga,btnEmpat,btnLima;

    @Override
    public void onStart() {
        super.onStart();
        utilitas = new Utilitas(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_menu,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view;
        init();
        event();
    }

    public void init() {
        btnSatu = v.findViewById(R.id.mMKategori);
        btnDua = v.findViewById(R.id.mMSatuan);
        btnTiga = v.findViewById(R.id.mMProduk);
        btnEmpat = v.findViewById(R.id.mMPelanggan);
        btnLima = v.findViewById(R.id.mMPegawai);
    }

    public void event() {
        btnSatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnKategori));
            }
        });
        btnDua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnSatuan));
            }
        });
        btnTiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnProduk));
            }
        });
        btnEmpat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnPelanggan));
            }
        });
        btnLima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnPegawai));
            }
        });
    }

}
