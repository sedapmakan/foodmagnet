package teamkeropok.com.foodmagnet.model;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 2016 on 19/4/2017.
 */

public class Kedai {

    public String IDkedai;
    public String nama_owner;
    public String nama_kedai;
    public String alamat;
    public String telefon;
    public String jenis_makanan;
    public String waktu_operasi_buka;
    public String waktu_operasi_tutup;
    public String julat_harga;
    public int ratingCount = 0;
    public Map<String, Boolean> rating = new HashMap<>();
    public boolean flagkedai;




    public Kedai()
    {
        //default constructor
    }

    public Kedai(String IDkedai, String nama_owner, String nama_kedai, String alamat, String telefon, String jenis_makanan, String waktu_operasi_buka, String waktu_operasi_tutup, String julat_harga)
    {
        this.IDkedai = IDkedai;
        this.nama_owner = nama_owner;
        this.nama_kedai = nama_kedai;
        this.alamat = alamat;
        this.telefon = telefon;
        this.jenis_makanan = jenis_makanan;
        this.waktu_operasi_buka = waktu_operasi_buka;
        this.waktu_operasi_tutup = waktu_operasi_tutup;
        this.julat_harga = julat_harga;
        this.flagkedai = false;



    }
/*
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


    public void setJenis_makanan(String jenis_makanan) {
        this.jenis_makanan = jenis_makanan;
    }
*/

    public String getJenis_makanan()
    {
        return jenis_makanan;
    }

    // [START post_to_map]
    @Exclude
    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("IDkedai", IDkedai);
        result.put("nama_owner", nama_owner);
        result.put("nama_kedai", nama_kedai);
        result.put("alamat", alamat);
        result.put("telefon", telefon);
        result.put("jenis_makanan", jenis_makanan);
        result.put("waktu_operasi_buka", waktu_operasi_buka);
        result.put("waktu_operasi_tutup", waktu_operasi_tutup);
        result.put("ratingCount", ratingCount);
        result.put("rating", rating);
        result.put("waktudibuat", ServerValue.TIMESTAMP);
        result.put("flagkedai", flagkedai);

        return result;
    }
    // [END post_to_map]


}

