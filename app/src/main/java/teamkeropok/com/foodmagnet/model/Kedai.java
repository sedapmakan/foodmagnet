package teamkeropok.com.foodmagnet.model;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 2016 on 19/4/2017.
 */

public class Kedai {

    private String IDkedai;
    String nama_owner;
    private String nama_kedai;
    private String alamat;
    private String telefon;
    private String jenis_makanan;
    public int ratingCount = 0;
    public Map<String, Boolean> rating = new HashMap<>();



    public Kedai()
    {
        //default constructor
    }

    public Kedai(String uid, String nama_owner, String nama_kedai, String alamat, String telefon, String jenis_makanan)
    {
        this.IDkedai = IDkedai;
        this.nama_owner = nama_owner;
        this.nama_kedai = nama_kedai;
        this.alamat = alamat;
        this.telefon = telefon;
        this.jenis_makanan = jenis_makanan;
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

    public String getJenis_makanan()
    {
        return jenis_makanan;
    }
    public void setJenis_makanan(String jenis_makanan) {
        this.jenis_makanan = jenis_makanan;
    }
*/
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
        result.put("ratingCount", rating);
        result.put("rating", rating);

        return result;
    }
    // [END post_to_map]


}

