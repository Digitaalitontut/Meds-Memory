package business;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.medsmemory.R;

public class ReminderPublisher extends BroadcastReceiver {
    public static String NOTIFICATION_EXTRA_KEY = "EXTRA_NOTIFICATION";
    public static String NOTIFICATION_ID_EXTRA_KEY = "EXTRA_NOTIFICATION_ID";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION_EXTRA_KEY);
        int id = intent.getIntExtra(NOTIFICATION_ID_EXTRA_KEY, 0);
        notificationManager.notify(id,notification);
    }
}
