package com.komputerkit.kasirtoko.Model;

/**
 * Created by msaifa on 08/03/2018.
 */

public class QBayarHutang {

    String idhutang,hutang,tglhutang,flaghutang,idpelanggan,pelanggan,alamat,nohp,saldodeposit,saldoHutang,idbayarhutang,tglbayar,jumlahbayar,sisahutang,tglmix ;

    public QBayarHutang(String idhutang, String hutang, String tglhutang, String flaghutang, String tglmix,String idpelanggan, String pelanggan, String alamat, String nohp, String saldodeposit, String saldoHutang, String idbayarhutang, String tglbayar, String jumlahbayar, String sisahutang) {
        this.idhutang = idhutang;
        this.hutang = hutang;
        this.tglhutang = tglhutang;
        this.flaghutang = flaghutang;
        this.tglmix = tglmix;
        this.idpelanggan = idpelanggan;
        this.pelanggan = pelanggan;
        this.alamat = alamat;
        this.nohp = nohp;
        this.saldodeposit = saldodeposit;
        this.saldoHutang = saldoHutang;
        this.idbayarhutang = idbayarhutang;
        this.tglbayar = tglbayar;
        this.jumlahbayar = jumlahbayar;
        this.sisahutang = sisahutang;
    }

    public String getTglmix() {
        return tglmix;
    }

    public String getIdhutang() {
        return idhutang;
    }

    public String getHutang() {
        return hutang;
    }

    public String getTglhutang() {
        return tglhutang;
    }

    public String getFlaghutang() {
        return flaghutang;
    }

    public String getIdpelanggan() {
        return idpelanggan;
    }

    public String getPelanggan() {
        return pelanggan;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNohp() {
        return nohp;
    }

    public String getSaldodeposit() {
        return saldodeposit;
    }

    public String getSaldoHutang() {
        return saldoHutang;
    }

    public String getIdbayarhutang() {
        return idbayarhutang;
    }

    public String getTglbayar() {
        return tglbayar;
    }

    public String getJumlahbayar() {
        return jumlahbayar;
    }

    public String getSisahutang() {
        return sisahutang;
    }
}
