package com.komputerkit.kasirtoko.Model;

/**
 * Created by msaifa on 25/02/2018.
 */

public class QProduk {

    String idproduk,kategori,produk,batchnumber,satuanbesar,hargabesar,satuankecil,hargakecil,stokbesar,flagproduk,ketkategori,ketorder ;
    int jumlah,satuan,flagAktif ;

    public QProduk(String idproduk, String kategori, String produk, String batchnumber, String satuanbesar, String hargabesar, String satuankecil, String hargakecil, String stokbesar, String flagproduk, String ketkategori, String ketorder, int jumlah, int satuan, int flagAktif) {
        this.idproduk = idproduk;
        this.kategori = kategori;
        this.produk = produk;
        this.batchnumber = batchnumber;
        this.satuanbesar = satuanbesar;
        this.hargabesar = hargabesar;
        this.satuankecil = satuankecil;
        this.hargakecil = hargakecil;
        this.stokbesar = stokbesar;
        this.flagproduk = flagproduk;
        this.ketkategori = ketkategori;
        this.ketorder = ketorder;
        this.jumlah = jumlah;
        this.satuan = satuan;
        this.flagAktif = flagAktif;
    }

    public String getIdproduk() {
        return idproduk;
    }

    public String getKategori() {
        return kategori;
    }

    public String getProduk() {
        return produk;
    }

    public String getBatchnumber() {
        return batchnumber;
    }

    public String getSatuanbesar() {
        return satuanbesar;
    }

    public String getHargabesar() {
        return hargabesar;
    }

    public String getSatuankecil() {
        return satuankecil;
    }

    public String getHargakecil() {
        return hargakecil;
    }

    public String getStokbesar() {
        return stokbesar;
    }

    public String getFlagproduk() {
        return flagproduk;
    }

    public String getKetkategori() {
        return ketkategori;
    }

    public String getKetorder() {
        return ketorder;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setKetorder(String ketorder) {
        this.ketorder = ketorder;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getSatuan() {
        return satuan;
    }

    public void setSatuan(int satuan) {
        this.satuan = satuan;
    }

    public int getFlagAktif() {
        return flagAktif;
    }

    public void setFlagAktif(int flagAktif) {
        this.flagAktif = flagAktif;
    }

    public void setHargabesar(String hargabesar) {
        this.hargabesar = hargabesar;
    }

    public void setHargakecil(String hargakecil) {
        this.hargakecil = hargakecil;
    }
}
