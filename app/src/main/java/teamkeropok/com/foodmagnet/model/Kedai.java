package teamkeropok.com.foodmagnet.model;


import com.google.firebase.database.IgnoreExtraProperties;
/**
 * Created by 2016 on 19/4/2017.
 */

public class Kedai {

    private String IDkedai;



    private String nama_kedai;
    private String alamat;
    private String telefon;
    private String jenis_makanan;


    public Kedai()
    {

    }

    public Kedai(String IDkedai, String nama_kedai, String alamat, String telefon, String jenis_makanan)
    {
        this.IDkedai = IDkedai;
        this.nama_kedai = nama_kedai;
        this.alamat = alamat;
        this.telefon = telefon;
        this.jenis_makanan = jenis_makanan;
    }

    public String getIDkedai() {
        return IDkedai;
    }

    public String getNama_kedai()
    {
        return nama_kedai;
    }
    public void setNama_kedai(String nama_kedai) {
        this.nama_kedai = nama_kedai;
    }


    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelefon() {
        return telefon;
    }
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getJenis_makanan()
    {
        return jenis_makanan;
    }
    public void setJenis_makanan(String jenis_makanan) {
        this.jenis_makanan = jenis_makanan;
    }

}
