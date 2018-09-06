package me.boqin.servicedemo;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import me.boqin.servicedemo.services.BinderService;

/**
 * TODO
 * Created by BoQin on 2018/9/5.
 * Modified by BoQin
 *
 * @Version
 */
public class TestActivity extends AppCompatActivity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BQ", "onCreate");
        setContentView(R.layout.activity_foreground);
        mButton = findViewById(R.id.foreground);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BinderService.class);
                startService(intent);// 如果不start，在activity被销毁后会自动unbind
                bindService(intent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        Intent lIntent = new Intent(getApplicationContext(), MainActivity.class);
                        lIntent.setAction(Intent.ACTION_MAIN);
                        lIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                        lIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ((BinderService.MyBinder)service).getService().notification(lIntent);
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                }, Service.BIND_AUTO_CREATE);
            }
        });
    }
}
