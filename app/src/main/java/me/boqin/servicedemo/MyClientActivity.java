package me.boqin.servicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import me.boqin.servicedemo.services.RemoteService;

/**
 * IPC通信 AIDL
 *
 * 单独一个进程开启服务处理事件
 *
 * Created by BoQin on 2018/10/16.
 * Modified by BoQin
 *
 * @Version
 */
public class MyClientActivity extends AppCompatActivity {
    protected Button mStart, mStop, mBind, mUnBind, mGo, mForeground;

    private IHelloAidlInterface myService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = IHelloAidlInterface.Stub.asInterface(service);
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

        setContentView(R.layout.activity_service);
        mStart = findViewById(R.id.start);
        mStop = findViewById(R.id.stop);
        mBind = findViewById(R.id.bind);
        mUnBind = findViewById(R.id.unbind);
        mGo = findViewById(R.id.go);
        mForeground = findViewById(R.id.foreground);

        mBind.setVisibility(View.GONE);
        mUnBind.setVisibility(View.GONE);
        mForeground.setVisibility(View.GONE);

        setClick();
    }

    protected void setClick() {
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RemoteService.class);
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
                if (myService!=null) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            getServiceInfo();
//                            Log.d("BQ", "1:"+Thread.currentThread().toString());
//                        }
//                    }).start();
                    // 耗时操作下回阻塞，帧丢失
                    getServiceInfo();
                    Log.d("BQ", "2:"+ Thread.currentThread().toString());
                }
            }
        });
    }

    private void getServiceInfo(){
        String result = null;
        try {
            result = myService.getProcess("hello");
            Log.d("BQ", result);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
