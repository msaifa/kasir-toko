package com.komputerkit.kasirtoko.Model;

/**
 * Created by msaifa on 06/03/2018.
 */

public class QHutang {

    String idhutang,hutang,tglhutang,flaghutang,idpelanggan,pelanggan,alamat,nohp,saldodeposit
,saldoHutang ;

    public QHutang(String idhutang, String hutang, String tglhutang, String flaghutang, String idpelanggan, String pelanggan, String alamat, String nohp, String saldodeposit, String saldoHutang) {
        this.idhutang = idhutang;
        this.hutang = hutang;
        this.tglhutang = tglhutang;
        this.flaghutang = flaghutang;
        this.idpelanggan = idpelanggan;
        this.pelanggan = pelanggan;
        this.alamat = alamat;
        this.nohp = nohp;
        this.saldodeposit = saldodeposit;
        this.saldoHutang = saldoHutang;
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
}
