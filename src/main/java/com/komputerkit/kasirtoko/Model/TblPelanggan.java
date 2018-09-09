package com.komputerkit.kasirtoko.Model;

/**
 * Created by msaifa on 20/01/2018.
 */

public class TblPelanggan {

    String idpelanggan, pelanggan, alamat, nohp, saldodeposit, hutang;

    public TblPelanggan(String idpelanggan, String pelanggan, String alamat, String nohp, String saldodeposit, String hutang) {
        this.idpelanggan = idpelanggan;
        this.pelanggan = pelanggan;
        this.alamat = alamat;
        this.nohp = nohp;
        this.saldodeposit = saldodeposit;
        this.hutang = hutang;
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

    public String getHutang() {
        return hutang;
    }

    public void setIdpelanggan(String idpelanggan) {
        this.idpelanggan = idpelanggan;
    }

    public void setPelanggan(String pelanggan) {
        this.pelanggan = pelanggan;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public void setSaldodeposit(String saldodeposit) {
        this.saldodeposit = saldodeposit;
    }
}