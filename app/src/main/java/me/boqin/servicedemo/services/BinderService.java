package me.boqin.servicedemo.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import me.boqin.servicedemo.R;
import me.boqin.servicedemo.TargetBean;

/**
 * 通过bind扩展接口
 * Created by BoQin on 2018/9/3.
 * Modified by BoQin
 *
 * @Version
 */
public class BinderService extends Service{

    private IBinder mBinder = new MyBinder();

    private TargetBean mTargetBean;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("BQ", "onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("BQ", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        Log.d("BQ", "onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d("BQ", "onDestroy");
        super.onDestroy();
    }

    public void setTargetBean(TargetBean targetBean){
        mTargetBean = targetBean;
    }

    public void printBean(){
        Log.d("BQ","TargetBean:"+mTargetBean.toString());
    }

    public void notification(Intent intent){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher).setDefaults(Notification.DEFAULT_LIGHTS)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentText("hello")
                .setContentIntent(PendingIntent.getActivity(this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        startForeground(1, builder.build());
    }

    public class MyBinder extends Binder {
        public BinderService getService(){
            return BinderService.this;
        }
    }
}
