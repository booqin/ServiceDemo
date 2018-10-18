package me.boqin.servicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import me.boqin.servicedemo.services.MessengerService;

/**
 * Messenger进行通信的Activity
 * Created by BoQin on 2018/10/18.
 * Modified by BoQin
 *
 * @Version
 */
public class MessengerActivity extends MyClientActivity{

    private Messenger mServiceMessenger;

    private Messenger mClientMessenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String result = msg.getData().getString("A");
                    Log.d("BQ", result);

            }

        }
    });

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mServiceMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setClick() {
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessengerService.class);
                getApplicationContext().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
            }
        });

        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(mServiceConnection);
            }
        });

        mGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mServiceMessenger !=null) {
                    //                    new Thread(new Runnable() {
                    //                        @Override
                    //                        public void run() {
                    //                            getServiceInfo();
                    //                            Log.d("BQ", "1:"+Thread.currentThread().toString());
                    //                        }
                    //                    }).start();
                    // 耗时操作下回阻塞，帧丢失
                    try {
                        Message message = Message.obtain();
                        message.what = 1;
                        message.replyTo = mClientMessenger;
                        mServiceMessenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
