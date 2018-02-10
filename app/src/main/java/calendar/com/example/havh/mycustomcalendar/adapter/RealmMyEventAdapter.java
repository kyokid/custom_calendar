package calendar.com.example.havh.mycustomcalendar.adapter;

import android.content.Context;

import calendar.com.example.havh.mycustomcalendar.model.MyEvent;
import io.realm.RealmResults;

/**
 * Created by HaVH on 2/9/18.
 */

public class RealmMyEventAdapter extends RealmModelAdapter<MyEvent> {

    public RealmMyEventAdapter(Context context, RealmResults<MyEvent> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }


}
