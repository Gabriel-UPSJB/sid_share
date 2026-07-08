package com.froggame.sid_share;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FcmService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // Obtener los datos de la notificación
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        // Crear una notificación
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "notificaciones")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true);

        // Crear un intent para cuando se haga clic en la notificación
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        notificationBuilder.setContentIntent(pendingIntent);

        // Obtener una referencia al NotificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        // Crear el canal de notificaciones (solo para Android O y versiones posteriores)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "El estado de reparación de su equipo";
            String description = "Verifica el estado de tu equipo en la reparacion que se le está ofreciendo.";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notificaciones", name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }

        // Mostrar la notificación
        notificationManager.notify(0, notificationBuilder.build());
    }

    @Override
    public void onNewToken(String token)
    {

    }
}
