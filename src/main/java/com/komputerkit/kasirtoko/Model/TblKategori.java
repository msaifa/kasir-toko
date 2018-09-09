package com.komputerkit.kasirtoko.Model;

/**
 * Created by msaifa on 18/02/2018.
 */

public class TblKategori {

    String kategori,ketkategori ;

    public TblKategori(String kategori, String ketkategori) {
        this.kategori = kategori;
        this.ketkategori = ketkategori;
    }

    public String getKategori() {
        return kategori;
    }

    public String getKetkategori() {
        return ketkategori;
    }
}
