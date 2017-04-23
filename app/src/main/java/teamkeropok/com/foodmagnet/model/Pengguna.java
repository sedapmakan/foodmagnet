package teamkeropok.com.foodmagnet.model;

import com.google.firebase.database.IgnoreExtraProperties;
// [START blog_user_class]
@IgnoreExtraProperties
public class Pengguna {


    public String nama_pengguna;





    private String password;
    private String email;

    public Pengguna() {
        // Default constructor required for calls to DataSnapshot.getValue(Pengguna.class)
    }

    public Pengguna(String nama_pengguna, String password, String email)
    {
        this.nama_pengguna = nama_pengguna;
        this.password = password;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }


}
