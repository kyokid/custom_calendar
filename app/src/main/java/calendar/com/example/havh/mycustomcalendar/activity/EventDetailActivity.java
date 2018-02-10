package calendar.com.example.havh.mycustomcalendar.activity;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import calendar.com.example.havh.mycustomcalendar.model.MyEvent;
import calendar.com.example.havh.mycustomcalendar.R;
import calendar.com.example.havh.mycustomcalendar.realm.RealmController;
import io.realm.Realm;

public class EventDetailActivity extends AppCompatActivity {
    Button btnSave;
    TextView tvName;
    EditText etStartTime, etEndTime;

    MyEvent myEvent;

    Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        realm = RealmController.getInstance().getRealm();

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString(MyEvent.KEY_EVENT);
        myEvent = RealmController.getInstance().getMyEvent(name);


        btnSave = findViewById(R.id.btnSaveEvent);
        tvName = findViewById(R.id.tvEventName);
        etStartTime = findViewById(R.id.etStartTime);
        etEndTime = findViewById(R.id.etEndTime);

        if (myEvent != null) {

            String dateFormat = "HH:mm";

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
            tvName.setText(myEvent.getName());
            String starTime = simpleDateFormat.format(myEvent.getStartTime());
            etStartTime.setText(starTime);
            etStartTime.setTag(starTime);
            etEndTime.setText(String.valueOf(myEvent.getDurationTime()));
        }


        etStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strArr [] = etStartTime.getText().toString().split(":");
                realm.beginTransaction();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(myEvent.getStartTime());
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strArr[0]));
                calendar.set(Calendar.MINUTE, Integer.parseInt(strArr[1]));

                myEvent.setStartTime(calendar.getTimeInMillis());
                realm.commitTransaction();
                finish();
//                myEvent.setStartTime();
            }
        });


    }

    private void showTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener callBack = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String s = hourOfDay + ":" + (minute == 0 ? "00" : minute);
                etStartTime.setText(s);
                etStartTime.setTag(s);
            }
        };

        String s = etStartTime.getTag() + "";
        String strArr[] = s.split(":");
        int hour = Integer.parseInt(strArr[0]);
        int min = Integer.parseInt(strArr[1]);
        TimePickerDialog dialog = new TimePickerDialog(EventDetailActivity.this, callBack, hour, min, true);
        dialog.setTitle("Edit time");
        dialog.show();
    }
}
