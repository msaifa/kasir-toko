package com.komputerkit.kasirtoko;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.komputerkit.kasirtoko.Utilitas.Utilitas;

public class MMlaporan extends Fragment {

    Utilitas utilitas;
    View v;
    ConstraintLayout btnSatu,btnDua,btnTiga,btnEmpat,btnLima,btnEnam,btnTujuh,btnDelapan;

    @Override
    public void onStart() {
        super.onStart();
        utilitas = new Utilitas(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_laporan,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view;
        init();
        event();
    }

    public void init() {
        btnSatu = v.findViewById(R.id.lLapProduk);
        btnDua = v.findViewById(R.id.lLapPelanggan);
        btnTiga = v.findViewById(R.id.lLapPegawai);
        btnEmpat = v.findViewById(R.id.lLapDeposit);
        btnLima = v.findViewById(R.id.lLapPenjualan);
        btnEnam = v.findViewById(R.id.lLapDataPenjualan);
        btnTujuh = v.findViewById(R.id.lLapHutang);
        btnDelapan = v.findViewById(R.id.lLapBayarHutang);
    }

    public void event() {
        btnSatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnLapProduk));
            }
        });
        btnDua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnLapPelanggan));
            }
        });
        btnTiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnLapPegawai));
            }
        });
        btnEmpat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnLapTopup));
            }
        });
        btnLima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnLapPenjualan));
            }
        });
        btnEnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnLapPenjualan));
            }
        });
        btnTujuh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnLapHutang));
            }
        });
        btnDelapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnLapBayarHutang));
            }
        });
    }

}
