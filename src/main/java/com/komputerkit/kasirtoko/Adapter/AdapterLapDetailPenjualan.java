package com.komputerkit.kasirtoko.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.komputerkit.kasirtoko.Model.QDetailOrder;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 08/03/2018.
 */


public class AdapterLapDetailPenjualan extends RecyclerView.Adapter<AdapterLapDetailPenjualan.ViewHolder> {

    ArrayList<QDetailOrder> data;

    public AdapterLapDetailPenjualan(ArrayList<QDetailOrder> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lap_detail_penjualan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String jum = Utilitas.perkalian(data.get(position).getJumlahorder(),data.get(position).getHargaorder()) ;

        holder.tvFaktur.setText(data.get(position).getFaktur());
        holder.tvBarang.setText(data.get(position).getProduk());
        holder.tvHarga.setText("Rp. "+data.get(position).getHargaorder());
        holder.tvJumlah.setText(data.get(position).getJumlahorder() + " " + data.get(position).getSatuan()) ;
        holder.tvTotal.setText("Rp. " + Utilitas.removeE(jum)) ;

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFaktur, tvBarang, tvHarga,tvJumlah, tvTotal;

        public ViewHolder(View itemView) {
            super(itemView);

            tvFaktur = itemView.findViewById(R.id.tvFaktur) ;
            tvBarang = itemView.findViewById(R.id.tvBarang) ;
            tvHarga = itemView.findViewById(R.id.tvHarga) ;
            tvJumlah = itemView.findViewById(R.id.tvJumlah) ;
            tvTotal = itemView.findViewById(R.id.tvTotal) ;

        }
    }
}