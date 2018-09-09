package com.komputerkit.kasirtoko.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.komputerkit.kasirtoko.Fragment.Master.MSatuan;
import com.komputerkit.kasirtoko.Fragment.Transaksi.TPilihHutang;
import com.komputerkit.kasirtoko.Model.QHutang;
import com.komputerkit.kasirtoko.Model.TblSatuan;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 06/03/2018.
 */

public class AdapterPilihHutang extends RecyclerView.Adapter<AdapterPilihHutang.ViewHolder>{

    ArrayList<QHutang> data ;
    Utilitas utilitas ;
    TPilihHutang fragment;

    public AdapterPilihHutang(TPilihHutang fragment, ArrayList<QHutang> data) {
        this.data = data;
        this.fragment = fragment ;

        utilitas = new Utilitas(fragment.getActivity()) ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hutang,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tvPelanggan.setText(data.get(position).getPelanggan()) ;
        holder.tvTanggal.setText(data.get(position).getTglhutang()) ;
        holder.tvJumlah.setText(Utilitas.removeE(data.get(position).getHutang())) ;

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

        TextView tvPelanggan,tvTanggal,tvJumlah;
        CardView wadah ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvJumlah = itemView.findViewById(R.id.tvJumlah) ;
            tvPelanggan = itemView.findViewById(R.id.tvPelanggan) ;
            tvTanggal = itemView.findViewById(R.id.tvTanggal) ;
            wadah = itemView.findViewById(R.id.wadah) ;
        }
    }
}