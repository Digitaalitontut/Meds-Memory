package business;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.medsmemory.Application;
import com.example.medsmemory.R;
import com.example.medsmemory.Reminder;

/**
 * Service that will create and show notifications
 */
public class NotificationService extends Service {
    private final String CHANNEL_ID = "MEDS_MEMORY_CHANNEL";

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Creates notification and starts foreground service
     * @param intent intent that should contain RemindAlarm.EXTRA_NOTIFICATION_KEY
     * @param flags flags
     * @param startId start id
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long id = intent.getLongExtra(RemindAlarm.EXTRA_NOTIFICATION_KEY, 0);
        createNotificationChannel();
        Intent scheduledIntent = new Intent(this, Reminder.class);
        scheduledIntent.putExtra(RemindAlarm.EXTRA_NOTIFICATION_KEY, id);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, (int) id,
                scheduledIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Medication med = MedicationStorage.getInstance().get(id);
        startForeground((int) id, createNotification(med, fullScreenPendingIntent));

        mediaPlayer.start();

        return START_STICKY; // TODO What is this?
    }

    /**
     * Creates notification
     * @param med Medication for the notification
     * @param intent pending intent that should be the reminder
     * @return returns created notification
     */
    private Notification createNotification(Medication med, PendingIntent intent) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(Application.getAppContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_stat_report)
                        .setContentTitle("Remember to take your medication!")
                        .setContentText("You need to take " + med.getDose() + " pill of " + med.getName())
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setOngoing(true)
                        .setAutoCancel(true)
                        .setCategory(NotificationCompat.CATEGORY_ALARM)
                        .setFullScreenIntent(intent, true)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        return builder.build();
    }

    /**
     * Creates notification channel for the notifications
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Meds Memory",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Displays Meds Memory notifications");
            NotificationManager manager =
                    Application.getAppContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
    }
}
