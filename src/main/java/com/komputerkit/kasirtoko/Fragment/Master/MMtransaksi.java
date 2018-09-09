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

public class MMtransaksi extends Fragment {

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
        return inflater.inflate(R.layout.fragment_main_transaksi,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view;
        init();
        event();
    }

    public void init() {
        btnSatu = v.findViewById(R.id.mTStok);
        btnDua = v.findViewById(R.id.mTDeposit);
        btnTiga = v.findViewById(R.id.mTPenjualan);
        btnEmpat = v.findViewById(R.id.mTBayarHutang);
    }

    public void event() {
        btnSatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnTambahStok));
            }
        });
        btnDua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnTopup));
            }
        });
        btnTiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnPenjualan));
            }
        });
        btnEmpat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.komputerkit.kasirtoko.ActivityUtama)getActivity()).pindah(getString(R.string.mnBayarUtang));
            }
        });

    }

}
