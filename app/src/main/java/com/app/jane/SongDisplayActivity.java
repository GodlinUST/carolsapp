package com.app.jane;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SongDisplayActivity extends AppCompatActivity {
    private long enqueue;
    private DownloadManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_display);

        TextView textView = (TextView) findViewById(R.id.song_display_view);
        Bundle bundle = getIntent().getExtras();
        String data = bundle.get("data").toString();
        //Toast.makeText(this, data, Toast.LENGTH_LONG).show();
        /*DB call need to given here,ata need to get shown from DB*/
        textView.setText(R.string.hark_the_herald);
        final String dataContent = (String) textView.getText();

        Button downloadBtn = findViewById(R.id.download);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeToFile(dataContent, SongDisplayActivity.this);

            }
        });
        Button playBtn = findViewById(R.id.play);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SongDisplayActivity.this, MusicServiceClient.class);
                startActivity(intent);
            }
        });

    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            notificationBuilder();
            } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void notificationBuilder()
    {
        createNotificationChannel();
        Intent intent = new Intent(this, SongDisplayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHRISTMAS")
                .setSmallIcon(R.drawable.christmas_background_logo)
                .setContentTitle("Christmas carols")
                .setContentText("Carols songs downloaded")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Carols songs downloaded"))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Toast.makeText(SongDisplayActivity.this, "DOWNLOAD COMPLETED", Toast.LENGTH_LONG).show();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHRISTMAS", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
