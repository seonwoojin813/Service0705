package com.tjoeun.service0705;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    //서비스가 만들어 질 때 호출되는 메소드
    @Override
    public void onCreate(){
        Log.e("service", "시작");
    }

    //백그라운드에서 작업을 수행할 메소드
    //첫번째 매개변수는 이 서비스를 호출할 때 사용한 인텐트
    //두번째 매개변수는 인텐트를 만들 때 설정한 옵션
    //세번째 매개변수는 서비스를 구분하기 위한 아이디
    @Override
    public int onStartCommand(
            Intent intent, int flags, int startId){
        Log.e("service", "작업 시작");
        int start = intent.getIntExtra("num", 0);
        Thread th = new Thread(){
            public void run() {
                for (int i=0; i<10; i=i+1){
                    try{
                        Thread.sleep(1000);
                        Log.e("작업중", i*10 + "%");
                    }catch (Exception e){

                    }
                }
                //작업이 종료되면 이 메소드를 호출해서 서비스를 중지시켜야합니다.
                stopSelf();
            }
        };
        th.start();
        //여기서 리턴하는 값은 재시작과 관련된 옵션
        //소멸되고 난 후 빨리 다시 시작되어야 한다고 설
        return Service.START_STICKY;
        //위 옵션 이외에도 START_NOT_STICKY : 다시 시작하지 않겠다는 옵션
        //START_REDELIVER_INTENT: 현재의 인텐트를 onStartCommand에게
        //다시 전송해서 서비스가 다시 시작되도록 설정

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("service", "바인딩");
        throw null;
    }

    @Override
    public void onDestroy(){
        Log.e("service", "종료");
    }
}
