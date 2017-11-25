package com.davis.ndktest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ServiceConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    // Example of a call to a native method
    TextView tv = (TextView) findViewById(R.id.sample_text);
    tv.setText(BuildConfig.VERSION_NAME);


        conn=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
//                myService=IMyAidlService.Stub.asInterface(service);
//                try {
//                    myService.basicService(9);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };



    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
