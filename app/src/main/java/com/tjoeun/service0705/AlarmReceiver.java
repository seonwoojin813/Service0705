package com.tjoeun.service0705;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "알람 호출", Toast.LENGTH_LONG).show();

        MediaPlayer player = MediaPlayer.create(context, R.raw.sample);
        player.start();
    }
}
