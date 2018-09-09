package com.komputerkit.kasirtoko.Model;

/**
 * Created by msaifa on 08/03/2018.
 */

public class QDeposit {
    String idpelanggan,pelanggan,alamat,nohp,saldodeposit,hutang,iddeposit,deposit,tgldeposit,ketdeposit ;

    public QDeposit(String idpelanggan, String pelanggan, String alamat, String nohp, String saldodeposit, String hutang, String iddeposit, String deposit, String tgldeposit, String ketdeposit) {
        this.idpelanggan = idpelanggan;
        this.pelanggan = pelanggan;
        this.alamat = alamat;
        this.nohp = nohp;
        this.saldodeposit = saldodeposit;
        this.hutang = hutang;
        this.iddeposit = iddeposit;
        this.deposit = deposit;
        this.tgldeposit = tgldeposit;
        this.ketdeposit = ketdeposit;
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

    public String getIddeposit() {
        return iddeposit;
    }

    public String getDeposit() {
        return deposit;
    }

    public String getTgldeposit() {
        return tgldeposit;
    }

    public String getKetdeposit() {
        return ketdeposit;
    }
}
