package com.example.pannonicatime;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import java.util.Random;

public class PodsjetnikReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String kanalId = "kanal_random_podsjetnici";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    kanalId,
                    "Dnevne obavijesti",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Zanimljive poruke i podsjetnici u toku dana");
            notificationManager.createNotificationChannel(channel);
        }

        Intent klikIntent = new Intent(context, MainActivity.class);
        klikIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, klikIntent, PendingIntent.FLAG_IMMUTABLE
        );

        String[][] poruke = {
                {"☀️ Pozdrav sa Panonike!", "Jesi li danas planirao prošetati pored jezera i udahnuti svjež zrak?"},
                {"🌊 Trenutak za opuštanje", "Mali predah uz omiljeno piće pored vode zvuči savršeno, zar ne?"},
                {"🦆 Pannonica Time", "Sjeti se da izdvojiš malo vremena samo za sebe danas. Uživaj u danu!"},
                {"🍦 Vrijeme je za pauzu", "Koja ti je omiljena aktivnost na slanim jezerima? Svrrati i provjeri šta ima novo!"},
                {"🌅 Prelijep dan vani", "Iskoristi slobodno vrijeme na najbolji mogući način. Vidimo se na jezeru!"}
        };

        Random random = new Random();
        int index = random.nextInt(poruke.length);
        String naslov = poruke[index][0];
        String tekst = poruke[index][1];

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, kanalId)
                .setSmallIcon(R.drawable.talas)
                .setContentTitle(naslov)
                .setContentText(tekst)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(tekst))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(200, builder.build());
    }
}