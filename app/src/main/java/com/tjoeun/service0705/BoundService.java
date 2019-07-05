package com.tjoeun.service0705;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.text.SimpleDateFormat;

public class BoundService extends Service {
    //BoundService를 만들기 위한 Binder 클래스를 생성하고
    //getService()를 재정의해서 현재 서비스 객체를 리턴
    public class MyLocationBinder extends Binder {
        BoundService getService() {
            return BoundService.this;
        }
    }

    //위에서 만든 클래스의 인스턴스를 생성
    IBinder myBinder = new MyLocationBinder();

    public BoundService() {

    }


    @Override
    //StartService에서는 이 메소드가 별 의미가 없지만
    //BoundService에서는 이 메소드가 중요

    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    //다른 프로세스에서 호출할 메소드 만들기
    public String getCurrentTime() {
        try {
            Thread.sleep(10000);
        } catch (Exception e) {

        }
            //현재 날짜 및 시간을 문자열로 만들기
            SimpleDateFormat dateFormat =
                    new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String currentTime = dateFormat.format(
                    new java.util.Date());
            return currentTime;

        }
    }
