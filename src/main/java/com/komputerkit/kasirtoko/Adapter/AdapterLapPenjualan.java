package com.komputerkit.kasirtoko.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.komputerkit.kasirtoko.Fragment.Laporan.LPenjualan;
import com.komputerkit.kasirtoko.Model.QOrder;
import com.komputerkit.kasirtoko.Model.QProduk;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 08/03/2018.
 */

public class AdapterLapPenjualan extends RecyclerView.Adapter<AdapterLapPenjualan.ViewHolder> {

    ArrayList<QOrder> data;
    Utilitas utilitas;
    LPenjualan lPenjualan ;

    public AdapterLapPenjualan(LPenjualan lPenjualan, ArrayList<QOrder> data) {
        this.data = data;
        this.lPenjualan = lPenjualan ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lap_penjualan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tvFaktur.setText(data.get(position).getFaktur());
        holder.tvTangal.setText(data.get(position).getTglorder());
        holder.tvPelanggan.setText(data.get(position).getPelanggan());
        holder.tvJumlah.setText("Rp. " + Utilitas.removeE(data.get(position).getTotalorder()));
        holder.tvMetode.setText(data.get(position).getKetorder());

        holder.wPrinter.setVisibility(View.VISIBLE);
        holder.wPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lPenjualan.print(data.get(position).getFaktur()) ;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFaktur,tvTangal,tvPelanggan,tvJumlah,tvMetode ;
        ConstraintLayout wPrinter ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvFaktur = itemView.findViewById(R.id.tvFaktur) ;
            tvTangal = itemView.findViewById(R.id.tvTanggal) ;
            tvPelanggan = itemView.findViewById(R.id.tvPelanggan) ;
            tvJumlah = itemView.findViewById(R.id.tvJumlah) ;
            tvMetode = itemView.findViewById(R.id.tvMetode) ;
            wPrinter = itemView.findViewById(R.id.wPrint) ;

        }
    }
}