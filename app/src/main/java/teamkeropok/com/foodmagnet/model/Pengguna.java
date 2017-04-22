package teamkeropok.com.foodmagnet.model;

/**
 * Created by 2016 on 19/4/2017.
 */

public class Pengguna {

    private String nama_pengguna;
    private String password;
    private String email;



    public Pengguna(String nama_pengguna, String password, String email)
    {
        this.nama_pengguna = nama_pengguna;
        this.password = password;
        this.email = email;
    }


    public String getNama_pengguna()
    {
        return nama_pengguna;
    }

    public String getPassword()
    {
        return password;
    }

    public String getEmail()
    {
        return email;
    }

}
