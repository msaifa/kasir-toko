package com.komputerkit.kasirtoko.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.komputerkit.kasirtoko.DialogFragment.DPilihPegawai;
import com.komputerkit.kasirtoko.DialogFragment.DcartPelanggan;
import com.komputerkit.kasirtoko.Model.TblPegawai;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 04/03/2018.
 */

public class AdapterPilhPegawai extends RecyclerView.Adapter<AdapterPilhPegawai.ViewHolder>{

    ArrayList<TblPegawai> data ;
    Utilitas utilitas ;
    DPilihPegawai dPilihPegawai;
    DcartPelanggan dpegawai;

    public AdapterPilhPegawai(DPilihPegawai dPilihPegawai, ArrayList<TblPegawai> data) {
        this.data = data;
        this.dPilihPegawai = dPilihPegawai ;

        utilitas = new Utilitas(dPilihPegawai.getActivity()) ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelanggan,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvPelanggan.setText(data.get(position).getPegawai());
        holder.tvAlamat.setText(data.get(position).getAlamatpegawai());
        holder.tvTelp.setText(data.get(position).getNohppegawai());
        holder.wOpsi.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dPilihPegawai.pilih(data.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvPelanggan,tvAlamat,tvTelp ;
        ConstraintLayout wOpsi;

        public ViewHolder(View itemView) {
            super(itemView);

            tvPelanggan = itemView.findViewById(R.id.tvPelanggan) ;
            tvTelp = itemView.findViewById(R.id.tvTelp) ;
            tvAlamat= itemView.findViewById(R.id.tvAlamat) ;
            wOpsi = itemView.findViewById(R.id.wSampah) ;
        }
    }
}