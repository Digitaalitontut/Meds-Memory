package business;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;

import com.example.medsmemory.Application;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class DayRemindReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra(RemindAlarm.EXTRA_NOTIFICATION_KEY, 0);
        Medication med = MedicationStorage.getInstance().get(id);

        if(med.getEnd().getTimeInMillis() <= System.currentTimeMillis()) {
            RemindAlarm.getInstance().cancelReminder(id, DayRemindReceiver.class);
            return; // TODO: Do we notify user at the end?
        }

        if(med.getTakeInterval() > 1) {
            createDayAlarms(med);
        }else {
            createNotification(context, med);
        }
    }

    private void createNotification(Context context, Medication med) {
        Intent service = new Intent(Application.getAppContext(), NotificationService.class);
        service.putExtra(RemindAlarm.EXTRA_NOTIFICATION_KEY, med.getId());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(service);
        }else {
            context.startService(service);
        }
    }


    private void createDayAlarms(Medication med) {
        AlarmManager alarmManager = (AlarmManager) Application.getAppContext().getSystemService(Context.ALARM_SERVICE);

        Intent hourlyIntent = new Intent(Application.getAppContext(), ReminderReceiver.class);
        hourlyIntent.putExtra(RemindAlarm.EXTRA_NOTIFICATION_KEY, med.getId());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(Application.getAppContext(), (int)med.getId(), hourlyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long interval = TimeUnit.HOURS.toMillis(12) / (long)(med.getTakeInterval() - 1);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), interval, pendingIntent);
    }
}
