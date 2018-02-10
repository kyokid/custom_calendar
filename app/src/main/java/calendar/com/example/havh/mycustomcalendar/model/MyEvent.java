package calendar.com.example.havh.mycustomcalendar.model;

import java.util.Calendar;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by HaVH on 2/8/18.
 */

public class MyEvent extends RealmObject {

    public static final String KEY_EVENT = "KEY_EVENT";

    @PrimaryKey
    private String name;


    private long startTime = 0L;//in milli seconds
    private int durationTime = 0; //in seconds

    @Ignore
    private Calendar startTimeCalendar;



    public MyEvent(){
        this.name = "default";
    }


    public MyEvent(String name) {
        this.name = name;
    }

    /*protected MyEvent() {
        name = in.readString();
        startTime = in.readInt();
        durationTime = in.readInt();

        startTimeCalendar = new GregorianCalendar();
        startTimeCalendar.setTimeInMillis(this.startTime);
    }*/
//     public static final Creator<MyEvent> CREATOR = new Creator<MyEvent>() {
//        @Override
//        public MyEvent createFromParcel(Parcel in) {
//            return new MyEvent(in);
//        }
//
//        @Override
//        public MyEvent[] newArray(int size) {
//            return new MyEvent[size];
//        }
//    };


    public static int getStartTimeAsHourOfDay(MyEvent myEvent) {
        return myEvent.startTimeCalendar.get(Calendar.HOUR_OF_DAY);
    }

    public static float getStartTimeAsFloatHourOfDay(MyEvent myEvent) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(myEvent.getStartTime());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        float result = hour + (min / 60f);
        return result;
//        return secondToHour(milliSecondToSecond(myEvent.getStartTime()));
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int endTime) {
        this.durationTime = endTime;
    }


    public static float milliSecondToSecond(long milliSecond) {
        return milliSecond / 1000.0f;
    }

    public static float secondToHour(float second) {
        return secondToMinute(second) / 60;
    }

    public static float secondToMinute(float second) {
        return second / 60.0f;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(name);
//        dest.writeLong(startTime);
//        dest.writeLong(durationTime);
//        dest.writeFloat(x);
//        dest.writeFloat(y);
//        dest.writeFloat(width);
//        dest.writeFloat(height);
//        dest.writeByte((byte) (isSelected ? 1 : 0));
//    }
}
