package me.boqin.servicedemo;

import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * IPC通信 AIDL
 * Created by BoQin on 2018/10/16.
 * Modified by BoQin
 *
 * @Version
 */
public class MyClientActivity extends AppCompatActivity {
    private Button mStart, mStop, mBind, mUnBind, mGo, mForeground;

    private IHelloAidlInterface myService;

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mStart = findViewById(R.id.start);
        mStop = findViewById(R.id.stop);
        mBind = findViewById(R.id.bind);
        mUnBind = findViewById(R.id.unbind);
        mGo = findViewById(R.id.go);
        mForeground = findViewById(R.id.foreground);
    }
}
