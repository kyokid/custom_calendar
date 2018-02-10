package calendar.com.example.havh.mycustomcalendar.adapter;

import android.support.v7.widget.RecyclerView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;

/**
 * Created by HaVH on 2/9/18.
 */

public abstract class RealmRecyclerViewAdapter<T extends RealmObject> extends RecyclerView.Adapter {

    private RealmBaseAdapter<T> realmBaseAdapter;

    public T getItem(int position) {
        return realmBaseAdapter.getItem(position);
    }

    public RealmBaseAdapter<T> getRealmBaseAdapter() {
        return realmBaseAdapter;
    }

    public void setRealmBaseAdapter(RealmBaseAdapter<T> realmAdapter) {
        realmBaseAdapter = realmAdapter;
    }

}
