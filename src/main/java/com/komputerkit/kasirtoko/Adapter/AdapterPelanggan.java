package com.komputerkit.kasirtoko.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.komputerkit.kasirtoko.Fragment.Master.MPelanggan;
import com.komputerkit.kasirtoko.Model.TblPelanggan;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 20/01/2018.
 */

public class AdapterPelanggan extends RecyclerView.Adapter<AdapterPelanggan.ViewHolder>{

    ArrayList<TblPelanggan> data ;
    Utilitas utilitas ;
    MPelanggan mPelanggan;

    public AdapterPelanggan(MPelanggan mPelanggan, ArrayList<TblPelanggan> data) {
        this.data = data;
        this.mPelanggan = mPelanggan ;

        utilitas = new Utilitas(mPelanggan.getActivity()) ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelanggan,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvPelanggan.setText(data.get(position).getPelanggan());
        holder.tvAlamat.setText(data.get(position).getAlamat());
        holder.tvTelp.setText(data.get(position).getNohp());

        holder.wOpsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilitas.showPopUp(v,R.menu.menu_opsi).setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId() ;

                        if (id == R.id.iHapus){
                            mPelanggan.hapus(data.get(position).getIdpelanggan());
                        } else if (id == R.id.iUbah){
                            mPelanggan.ubah(data.get(position).getIdpelanggan());
                        }

                        return false;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvPelanggan,tvAlamat,tvTelp ;
        ConstraintLayout wOpsi ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvPelanggan = itemView.findViewById(R.id.tvPelanggan) ;
            tvTelp = itemView.findViewById(R.id.tvTelp) ;
            tvAlamat= itemView.findViewById(R.id.tvAlamat) ;
            wOpsi = itemView.findViewById(R.id.wSampah) ;
        }
    }
}
