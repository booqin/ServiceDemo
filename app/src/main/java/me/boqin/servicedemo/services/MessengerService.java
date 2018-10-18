package me.boqin.servicedemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Messenger实现通信
 * Created by BoQin on 2018/10/18.
 * Modified by BoQin
 *
 * @Version
 */
public class MessengerService extends Service{

    private final Messenger mMessenger = new Messenger(new MyHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    private static class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    try {
                        Message message = Message.obtain();
                        message.what = 1;
                        message.getData().putString("A", "hello");
                        msg.replyTo.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
