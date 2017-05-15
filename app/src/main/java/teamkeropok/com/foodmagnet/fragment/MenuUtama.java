package teamkeropok.com.foodmagnet.fragment;

import android.support.v4.app.Fragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
/**
 * Created by 2016 on 23/4/2017.
 *
 * Menu utama get value from List Kedai Fragment
 */

public class MenuUtama extends ListKedaiFragment {

    public MenuUtama() {}




    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START all_kedai_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query menuUtamaQuery = databaseReference.child("Kedai")
                .limitToFirst(100);
        // [END recent_posts_query]

        // TODO: Query based on range can start from here by puttin range 1 - 10 km radius.

        return menuUtamaQuery;
    }
}
