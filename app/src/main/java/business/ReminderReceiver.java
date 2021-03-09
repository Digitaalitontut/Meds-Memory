package business;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.medsmemory.Application;
import com.example.medsmemory.R;
import com.example.medsmemory.Reminder;

/**
 * BroadcastReceiver that creates notifications when alarms go off
 */
public class ReminderReceiver extends BroadcastReceiver {


    /**
     * Creates service that will show the alarm
     * @param context Application context
     * @param intent intent that should contain RemindAlarm.EXTRA_NOTIFICATION_KEY
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(Application.getAppContext(), NotificationService.class);
        long id = intent.getLongExtra(RemindAlarm.EXTRA_NOTIFICATION_KEY, 0);
        Medication med = MedicationStorage.getInstance().get(id);
        if(MedicationStorage.getInstance().countLog(med, med.getStart(), med.getEnd()) >= med.getTakeInterval()) {
            RemindAlarm.getInstance().cancelReminder(id, ReminderReceiver.class);
            return;
        }

        service.putExtra(RemindAlarm.EXTRA_NOTIFICATION_KEY, id);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(service);
        }else {
            context.startService(service);
        }
        Log.d("Reminder:", "Trying to open");
    }
}
