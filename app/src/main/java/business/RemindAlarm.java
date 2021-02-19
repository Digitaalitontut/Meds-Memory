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

import java.util.Calendar;


public class RemindAlarm {
    private final String CHANNEL_ID = "MEDSMEMORYCHANNEL";

    private static RemindAlarm _instance = null;

    private RemindAlarm() {
    }

    public static RemindAlarm getInstance() {
        if(_instance == null){_instance = new RemindAlarm();}
        return _instance;
    }

    public void scheduleNotification(Calendar calendar) {
        createNotificationChannel();
        Notification notification = createNotification();

        Intent notificationIntent = new Intent(Application.getAppContext(), ReminderPublisher.class);
        notificationIntent.putExtra(ReminderPublisher.NOTIFICATION_ID_EXTRA_KEY, 12345); // TODO: Insert correct id
        notificationIntent.putExtra(ReminderPublisher.NOTIFICATION_EXTRA_KEY, notification);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(Application.getAppContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT); // TODO: We might need to change flag

        AlarmManager alarmManager = (AlarmManager)Application.getAppContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

    private Notification createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(Application.getAppContext(),CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_report)
                .setContentTitle("Remember to take your medication!")
                .setContentText("You need to take 1 pill of something")
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        return builder.build();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Meds Memory", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Displays Meds Memory notifications");
            NotificationManager manager = Application.getAppContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
