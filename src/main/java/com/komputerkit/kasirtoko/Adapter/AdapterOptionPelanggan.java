package com.komputerkit.kasirtoko.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.komputerkit.kasirtoko.DialogFragment.DPilihPelanggan;
import com.komputerkit.kasirtoko.Model.TblPelanggan;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

public class AdapterOptionPelanggan extends RecyclerView.Adapter<AdapterOptionPelanggan.ViewHolder> {

    ArrayList<TblPelanggan> data ;
    Utilitas utilitas ;
    DPilihPelanggan fragment;

    public AdapterOptionPelanggan(DPilihPelanggan fragment, ArrayList<TblPelanggan> data) {
        this.data = data;
        this.fragment = fragment;

        utilitas = new Utilitas(fragment.getActivity()) ;
    }

    @Override
    public AdapterOptionPelanggan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelanggan,parent,false) ;
        return new AdapterOptionPelanggan.ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final AdapterOptionPelanggan.ViewHolder holder, final int position) {
        holder.tvPelanggan.setText(data.get(position).getPelanggan());
        holder.tvAlamat.setText(data.get(position).getAlamat());
        holder.tvTelp.setText(data.get(position).getNohp());
        holder.wOpsi.setVisibility(View.GONE);

        holder.wadah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.pilih(data.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size() ;
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
