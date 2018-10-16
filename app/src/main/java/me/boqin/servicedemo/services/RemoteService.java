package me.boqin.servicedemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import me.boqin.servicedemo.IHelloAidlInterface;

/**
 * 关于AIDL的DEMO
 * 单独进程的服务
 * Created by BoQin on 2018/10/16.
 * Modified by BoQin
 *
 * @Version
 */
public class RemoteService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    private final IHelloAidlInterface.Stub myBinder =  new IHelloAidlInterface.Stub(){

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getProcess(String name) throws RemoteException {
            return Thread.currentThread().getName();
        }
    };
}
