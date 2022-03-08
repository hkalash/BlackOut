package com.smartsoftwaresolutions.blackout;

import android.content.Intent;
import android.os.PowerManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smartsoftwaresolutions.blackout.R;

public class MainActivity extends AppCompatActivity {

 //   private Button btn1,btn3;
    private Button btn3;
    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;
    private int field = 0x00000020;

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // setContentView(R.layout.activity_main);
       // tv=findViewById(R.id.tv2);
      //  SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
      //  Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
      // String s= String.valueOf(proximitySensor.getMaximumRange());
      // proximitySensor.getMaximumRange();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            String s1= String.valueOf(proximitySensor.isWakeUpSensor());
//            tv.setText(s1);
//        }
//
//        try {
//            // Yeah, this is hidden field.
//            field = PowerManager.class.getField("PROXIMITY_SCREEN_OFF_WAKE_LOCK").getInt(null);
//        } catch (Throwable ignored) {
//        }

       // powerManager = (PowerManager) getSystemService(POWER_SERVICE);
      //  wakeLock = powerManager.newWakeLock(field, getLocalClassName());


      //  btn1 = (Button) findViewById(R.id.btn1);
       // btn2 = (Button) findViewById(R.id.btn2);
btn3=findViewById(R.id.btn3);

btn3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       // Intent intent=new Intent(MainActivity.this,lock_phone.class);
        Intent intent=new Intent(MainActivity.this,Scean_Bright.class);
       // Intent intent=new Intent(MainActivity.this,Audio.class);
       // Intent intent=new Intent(MainActivity.this,SensorActivity.class);
        startActivity(intent);
    }
});

//        btn1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // if the wakelock is not active it will activate it
//                if(!wakeLock.isHeld()) {
//                 //   wakeLock.acquire(1*60*1000L /*10 minutes*/);
//                    wakeLock.acquire();
//                }
//            }
//        });
//
//        btn2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if(wakeLock.isHeld()) {
//                    wakeLock.release();
//                }
//            }
//        });
    }
}

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_POWER) {
//            Intent i = new Intent(this, Black.class);
//            startActivity(i);
//            return true;
//        }
//
//        return super.dispatchKeyEvent(event);
//    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (event.getAction() == KeyEvent.ACTION_DOWN) {
//
//            switch (event.getKeyCode()) {
//                case  KeyEvent.KEYCODE_DPAD_UP:
//                    showToast("UP pressed");
//                    return true;
//                case  KeyEvent.KEYCODE_DPAD_DOWN:
//                    showToast("DOWN pressed");
//                    return true;
//                case  KeyEvent.KEYCODE_DPAD_RIGHT:
//                    showToast("RIGHT pressed");
//                    return true;
//                case  KeyEvent.KEYCODE_DPAD_LEFT:
//                    showToast("LEFT pressed");
//                    return true;
//                case  KeyEvent.KEYCODE_DPAD_CENTER:
//                    showToast("CENTER pressed");
//                    return true;
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    void showToast(String msg)
//    {
//        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
//    }
//}
