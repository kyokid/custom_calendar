package calendar.com.example.havh.mycustomcalendar.realm;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import calendar.com.example.havh.mycustomcalendar.model.MyEvent;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by HaVH on 2/9/18.
 */

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    private ArrayList<MyEvent> myEventList = new ArrayList<>();
    private ArrayList<MyListEvent> myListEvents = new ArrayList<>();

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Activity activity) {
        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {
        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {
        return instance;
    }

    public void addMyListEvent(MyListEvent myListEvent) {

        this.myListEvents.add(myListEvent);
    }

    public Realm getRealm() {
        return realm;
    }

    public void refresh() {
        realm.refresh();
    }

    public void clearAll() {
        realm.beginTransaction();
        realm.clear(MyEvent.class);
        realm.commitTransaction();
    }

    public RealmResults<MyEvent> getMyEvents() {
        return realm.where(MyEvent.class).findAll();
    }

    public MyEvent getMyEvent(String name) {
        return realm.where(MyEvent.class).equalTo("name", name).findFirst();
    }

    public List<MyEvent> getMyEventList() {
        return this.myEventList;
    }

    //Load myEventList from disk
    public void loadMyEventList() {
        myEventList.clear();
        myEventList.addAll(getMyEvents());
        for (MyListEvent myListEvent : myListEvents)
            myListEvent.OnMyListChanged(myEventList);
    }

    //Save mYEventList to disk
    public void saveMyEventList() {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(myEventList);
        realm.commitTransaction();
        for (MyListEvent myListEvent : myListEvents)
            myListEvent.OnMyListChanged(myEventList);
    }

    //check isEmpty
    public boolean hasMyEvent() {
        return !realm.allObjects(MyEvent.class).isEmpty();
    }


    public interface MyListEvent {
        void OnMyListChanged(List<MyEvent> myEventList);
    }

}
