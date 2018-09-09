package com.komputerkit.kasirtoko.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.komputerkit.kasirtoko.fragment_Transaksi_penjualan;
import com.komputerkit.kasirtoko.Model.QProduk;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 20/01/2018.
 */


public class AdapterTransaksiPenjualan extends RecyclerView.Adapter<AdapterTransaksiPenjualan.ViewHolder> {

    ArrayList<QProduk> data ;
    Utilitas utilitas ;
    fragment_Transaksi_penjualan fragment;
    QProduk tblproduk;

    public AdapterTransaksiPenjualan(fragment_Transaksi_penjualan fragment, ArrayList<QProduk> data) {
        this.data = data;
        this.fragment = fragment ;

        utilitas = new Utilitas(fragment.getActivity()) ;
    }
    @Override
    public AdapterTransaksiPenjualan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tpenjualan,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final AdapterTransaksiPenjualan.ViewHolder holder, final int position) {
        double price ;

        if (data.get(position).getSatuan() == 1){
            price = Utilitas.strToDouble(data.get(position).getHargakecil()) ;
        } else {
            price = Utilitas.strToDouble(data.get(position).getHargabesar()) ; // ini bukan harga besar?
        }

        String harga = Utilitas.doubleToStr(price*Utilitas.strToDouble(String.valueOf(data.get(position).getJumlah())));
        String jumlah = Utilitas.doubleToStr(Utilitas.strToDouble(String.valueOf(data.get(position).getJumlah())));

        holder.tvProduk.setText(data.get(position).getProduk());
        holder.tvJumlah.setText(utilitas.removeE(harga));
        holder.tvTotalp.setText(utilitas.removeE(jumlah));
        holder.wSampah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.hapus(position);
            }//suda ?
        });
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvProduk,tvJumlah,tvTotalp;
        ConstraintLayout wSampah;

        public ViewHolder(View itemView) {
            super(itemView);

            tvProduk = itemView.findViewById(R.id.tvProduk);
            tvJumlah = itemView.findViewById(R.id.tvJumlah);
            tvTotalp = itemView.findViewById(R.id.tvTotal);
            wSampah = itemView.findViewById(R.id.wSampah);
        }
    }

}
