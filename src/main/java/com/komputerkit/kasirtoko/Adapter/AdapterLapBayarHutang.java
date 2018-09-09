package com.komputerkit.kasirtoko.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.komputerkit.kasirtoko.Model.QBayarHutang;
import com.komputerkit.kasirtoko.Model.QHutang;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 08/03/2018.
 */

public class AdapterLapBayarHutang extends RecyclerView.Adapter<AdapterLapBayarHutang.ViewHolder>{

    ArrayList<QBayarHutang> data ;
    Utilitas utilitas ;

    public AdapterLapBayarHutang(ArrayList<QBayarHutang> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lap_pelanggan,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvPelanggan.setText(data.get(position).getPelanggan());
        holder.tvAlamat.setText(data.get(position).getTglbayar());
        holder.tvDeposit.setVisibility(View.GONE);
        holder.tDeposit.setVisibility(View.GONE);
        holder.tvHutang.setText("Rp. " + Utilitas.removeE(data.get(position).getJumlahbayar()));
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvPelanggan,tvAlamat, tvDeposit, tvHutang,tDeposit ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvPelanggan = itemView.findViewById(R.id.tvPelanggan) ;
            tvDeposit = itemView.findViewById(R.id.tvDeposit) ;
            tDeposit = itemView.findViewById(R.id.tDeposit) ;
            tvAlamat= itemView.findViewById(R.id.tvAlamat) ;
            tvHutang = itemView.findViewById(R.id.tvHutang) ;
        }
    }
}