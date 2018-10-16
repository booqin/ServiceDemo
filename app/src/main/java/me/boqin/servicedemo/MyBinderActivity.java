package me.boqin.servicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import me.boqin.servicedemo.services.BinderService;

/**
 * 同进程的服务端binder通信
 * Created by BoQin on 2018/10/16.
 * Modified by BoQin
 *
 * @Version
 */
public class MyBinderActivity extends AppCompatActivity{

    private Button mStart, mStop, mBind, mUnBind, mGo, mForeground;

    private BinderService mBinderService;

    /** 成员变量时可以作为id，可以避免多次调用 */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderService = ((BinderService.MyBinder) service).getService();
            TargetBean targetBean = new TargetBean();
            targetBean.setId(1);
            targetBean.setName("hello");
            mBinderService.setTargetBean(targetBean);
            mBinderService.printBean();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinderService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStart = findViewById(R.id.start);
        mStop = findViewById(R.id.stop);
        mBind = findViewById(R.id.bind);
        mUnBind = findViewById(R.id.unbind);
        mGo = findViewById(R.id.go);
        mForeground = findViewById(R.id.foreground);


        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                if (mBinderService!=null) {
                //                    TargetBean targetBean = new TargetBean();
                //                    targetBean.setId(1);
                //                    targetBean.setName("hello");
                //                    mBinderService.setTargetBean(targetBean);
                //                    mBinderService.printBean();
                //                }
                Intent intent = new Intent(getApplicationContext(), BinderService.class);

                getApplicationContext().startService(intent);
            }
        });

        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BinderService.class);

                getApplicationContext().stopService(intent);
            }
        });

        mBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BinderService.class);

                getApplicationContext().bindService(intent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        mBinderService = ((BinderService.MyBinder) service).getService();
                        TargetBean targetBean = new TargetBean();
                        targetBean.setId(1);
                        targetBean.setName("hello");
                        mBinderService.setTargetBean(targetBean);
                        mBinderService.printBean();
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        mBinderService = null;
                    }
                }, BIND_AUTO_CREATE);
            }
        });

        mUnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApplicationContext().unbindService(new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        mBinderService = ((BinderService.MyBinder) service).getService();
                        TargetBean targetBean = new TargetBean();
                        targetBean.setId(1);
                        targetBean.setName("hello");
                        mBinderService.setTargetBean(targetBean);
                        mBinderService.printBean();
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        mBinderService = null;
                    }
                });
            }
        });

        mGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(v.getContext(), TestActivity.class);
                v.getContext().startActivity(mIntent);
                finish();
            }
        });

        mForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
