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

public class MainActivity extends AppCompatActivity {

    private Button mBinder, mMessenger, mAidl;

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
        mBinder = findViewById(R.id.binder);
        mMessenger = findViewById(R.id.messenger);
        mAidl = findViewById(R.id.aidl);


        mBinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyBinderActivity.class);
                getApplicationContext().startActivity(intent);
            }
        });

        mMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), BinderService.class);
//
//                getApplicationContext().stopService(intent);
            }
        });

        mAidl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyClientActivity.class);

                getApplicationContext().startActivity(intent);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
