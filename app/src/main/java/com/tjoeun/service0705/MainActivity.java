package com.tjoeun.service0705;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    //바운드 서비스 변수와 바운드 여부를 저장할 변수
    BoundService myService;
    boolean isBound;

    ServiceConnection myConnection = new ServiceConnection() {
        //서비스를 생성(연결)
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //서비스를 생성해서 myService에 연결
            BoundService.MyLocationBinder binder = (BoundService.MyLocationBinder)iBinder;
            myService = binder.getService();
            isBound = true;
        }


        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView resultView = (TextView)findViewById(R.id.resultview);
        Button boundservice = (Button)findViewById(R.id.boundservice);
        Intent intent1 = new Intent(MainActivity.this, BoundService.class);
        bindService(intent1, myConnection, Context.BIND_AUTO_CREATE);
        boundservice.setOnClickListener((view)->{
            //myService가 가진 메소드를 호출
            //이 Activity가 종료 되더라도 서비스의 메소드는 수행
            String currentTime = myService.getCurrentTime();
            resultView.setText(currentTime);
        });


        Button broadcastSender = (Button)findViewById(R.id.broadcastsender);
        broadcastSender.setOnClickListener((view)->{
            //com.example.sendbroadcast를 호출하도록 방송을 발송
            Intent intent = new Intent();
            intent.setAction("com.example.sendbroadcast");
            //한번도 화면에 보여진 적이 없는 경우에도 출력하도록 설정
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            //방송 발송
            sendBroadcast(intent);
        });

        Button startIntentService = (Button)findViewById(R.id.startintentservice);
        startIntentService.setOnClickListener((view)->{
            Intent intent = new Intent(
                    MainActivity.this, MyIntentService.class);
            startService(intent);

        });

        Button startService = (Button)findViewById(R.id.startservice);
           startIntentService.setOnClickListener((view)->{
               Intent intent = new Intent(
                       MainActivity.this, MyService.class);
               intent.putExtra("num", 100);
               startService(intent);
        });

           //알람 버튼을 눌렀을 때 동작할 코드
        Button alarmService = (Button)findViewById(R.id.alarmservice);
        alarmService.setOnClickListener((view)->{
            //알람관리 객체 생성
            AlarmManager am = (AlarmManager)getSystemService(
                    Context.ALARM_SERVICE);
            //알람에 사용할 인텐트 만들기
            Intent intent = new Intent(MainActivity.this,
            AlarmReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this,
                    0, intent, 0);
            //현재 시간에서 30초후 만들기
            //long amtime = System.currentTimeMilllis() + 30000;
            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.SECOND, 30);

            //알람 설정
            am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);


        });

    }
}
