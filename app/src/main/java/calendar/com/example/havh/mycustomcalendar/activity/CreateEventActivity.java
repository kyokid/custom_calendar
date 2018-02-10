package calendar.com.example.havh.mycustomcalendar.activity;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import calendar.com.example.havh.mycustomcalendar.R;
import calendar.com.example.havh.mycustomcalendar.model.MyEvent;
import calendar.com.example.havh.mycustomcalendar.realm.RealmController;
import io.realm.Realm;
import io.realm.RealmResults;

public class CreateEventActivity extends AppCompatActivity {

    Button btnCreateEvent;
    EditText etCreateStartTime, etFileName;

    List<MyEvent> myEvents = new ArrayList<>();
    RealmResults<MyEvent> realmMyEvent;

    Calendar newTimeEvent;

    boolean isOverlap;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        realm = Realm.getDefaultInstance();

        btnCreateEvent = findViewById(R.id.btnCreateEvent);
        etCreateStartTime = findViewById(R.id.etNewStartTime);
        etFileName = findViewById(R.id.etFileNameChoose);

        DateFormat df = new SimpleDateFormat("hh:mm");
        String current = df.format(Calendar.getInstance().getTime());

        etCreateStartTime.setText(current);
        etCreateStartTime.setTag(current);

        realmMyEvent = RealmController.getInstance().getMyEvents();
        myEvents = realmMyEvent;

        etCreateStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        newTimeEvent = Calendar.getInstance();
        isOverlap = false;

        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = etFileName.getText().toString();
                MyEvent newEvent = new MyEvent(fileName);
                String strArr[] = etCreateStartTime.getText().toString().split(":");
                newTimeEvent.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strArr[0]));
                newTimeEvent.set(Calendar.MINUTE, Integer.parseInt(strArr[1]));
                newEvent.setStartTime(newTimeEvent.getTimeInMillis());
                newEvent.setDurationTime(4000);
                newEvent.setName(fileName);

                for (MyEvent myEvent : myEvents) {
                    isOverlap = isOverlapEvent(newEvent, myEvent);
                    if (isOverlap) {
                        Toast.makeText(CreateEventActivity.this, "can not overlap event", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                if (!isOverlap) {
//                    realm.beginTransaction();
//                    realm.copyToRealmOrUpdate(newEvent);
//                    realm.commitTransaction();
                    RealmController.getInstance().getMyEventList().add(newEvent);
                    RealmController.getInstance().saveMyEventList();
                    finish();
                }
//                RealmController.getInstance().clearAll();

            }
        });
    }


    private boolean isOverlapEvent(MyEvent newEvent, MyEvent event) {
        boolean result = false;
        float sumLength = 0F;
        long distance1 = 0L;
        long distance2 = 0L;
        long maxDistance = 0L;
        long newEventStarTimeSecond = newEvent.getStartTime() / 1000L;
        long eventStarTimeSecond = event.getStartTime() / 1000L;
        //second type
        sumLength = newEvent.getDurationTime() + event.getDurationTime();
        distance1 = Math.abs(newEventStarTimeSecond - (eventStarTimeSecond + event.getDurationTime()));
        distance2 = Math.abs(eventStarTimeSecond - (newEventStarTimeSecond + newEvent.getDurationTime()));
        maxDistance = distance1 > distance2 ? distance1 : distance2;

        result = maxDistance < sumLength;
        return result;
    }

    private void showTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener callBack = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String s = hourOfDay + ":" + (minute == 0 ? "00" : minute);
                etCreateStartTime.setText(s);
                etCreateStartTime.setTag(s);
            }
        };

        String s = etCreateStartTime.getTag() + "";
        String strArr[] = s.split(":");
        int hour = Integer.parseInt(strArr[0]);
        int min = Integer.parseInt(strArr[1]);
        TimePickerDialog dialog = new TimePickerDialog(CreateEventActivity.this, callBack, hour, min, true);
        dialog.setTitle("Edit time");
        dialog.show();
    }
}
