package business;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.medsmemory.Application;
import com.example.medsmemory.R;
import com.example.medsmemory.Reminder;

import java.util.Calendar;


public class RemindAlarm {
    private final String CHANNEL_ID = "MEDSMEMORYCHANNEL";
    public static final String EXTRA_NOTIFICATION_KEY = "EXTRA_NOTIFICATION_KEY";

    private static RemindAlarm _instance = null;

    private RemindAlarm() {
    }

    public static RemindAlarm getInstance() {
        if(_instance == null){_instance = new RemindAlarm();}
        return _instance;
    }

    public void scheduleReminder(Medication medication) {

        Intent intent = new Intent(Application.getAppContext(), ReminderReceiver.class);
        intent.putExtra(RemindAlarm.EXTRA_NOTIFICATION_KEY, medication.getId());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(Application.getAppContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT); // TODO: We might need to change flag

        AlarmManager alarmManager = (AlarmManager)Application.getAppContext().getSystemService(Context.ALARM_SERVICE);



        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, medication.getStart().getTimeInMillis(),medication.getIntervalInMilliseconds(), pendingIntent);

    }
}
