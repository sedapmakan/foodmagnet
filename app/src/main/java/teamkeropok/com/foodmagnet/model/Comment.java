package teamkeropok.com.foodmagnet.model;

import com.google.firebase.database.IgnoreExtraProperties;

// [START comment_class]
@IgnoreExtraProperties
public class Comment {

    public String IDkedai;
    public String nama_pengomen;
    public String text;

    public Comment() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Comment(String IDkedai, String nama_pengomen, String text) {
        this.IDkedai = IDkedai;
        this.nama_pengomen = nama_pengomen;
        this.text = text;
    }

}
// [END comment_class]
