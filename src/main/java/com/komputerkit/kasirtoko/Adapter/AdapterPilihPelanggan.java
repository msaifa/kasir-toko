package com.komputerkit.kasirtoko.Adapter;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.komputerkit.kasirtoko.DialogFragment.DCartProduk;
import com.komputerkit.kasirtoko.DialogFragment.DcartPelanggan;
import com.komputerkit.kasirtoko.Model.QProduk;
import com.komputerkit.kasirtoko.Model.TblPegawai;
import com.komputerkit.kasirtoko.Model.TblPelanggan;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 28/02/2018.
 */

public class AdapterPilihPelanggan extends RecyclerView.Adapter<AdapterPilihPelanggan.ViewHolder>{

    ArrayList<TblPelanggan> data ;
    ArrayList<TblPegawai> datas ;
    Utilitas utilitas ;
    DcartPelanggan fragmentpelanggan;
    boolean pelanggan;

    public AdapterPilihPelanggan(DcartPelanggan fragmentpelanggan, ArrayList<TblPelanggan> data) {
        this.data = data;
        this.fragmentpelanggan = fragmentpelanggan ;

        utilitas = new Utilitas(fragmentpelanggan.getActivity()) ;
        pelanggan = true;
    }

    public AdapterPilihPelanggan(ArrayList<TblPegawai> datas,DcartPelanggan fragmentpelanggan) {
        this.datas = datas;
        this.fragmentpelanggan = fragmentpelanggan ;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelanggan,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (pelanggan){
            holder.tvPelanggan.setText(data.get(position).getPelanggan());
            holder.tvAlamat.setText(data.get(position).getAlamat());
            holder.tvTelp.setText(data.get(position).getNohp());
        } else {
            holder.tvPelanggan.setText(datas.get(position).getPegawai());
            holder.tvAlamat.setText(datas.get(position).getAlamatpegawai());
            holder.tvTelp.setText(datas.get(position).getNohppegawai());
        }

        holder.wOpsi.setVisibility(View.GONE);

        holder.wadah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pelanggan){
                    fragmentpelanggan.pilih(data.get(position));
                } else {
                    fragmentpelanggan.pilih(datas.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (pelanggan){
            return data.size() ;
        } else {
            return datas.size() ;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvPelanggan,tvAlamat,tvTelp ;
        ConstraintLayout wOpsi,wadah ;

        public ViewHolder(View itemView) {
            super(itemView);

            wadah = itemView.findViewById(R.id.wadah);
            tvPelanggan = itemView.findViewById(R.id.tvPelanggan) ;
            tvTelp = itemView.findViewById(R.id.tvTelp) ;
            tvAlamat= itemView.findViewById(R.id.tvAlamat) ;
            wOpsi = itemView.findViewById(R.id.wSampah) ;
        }
    }
}