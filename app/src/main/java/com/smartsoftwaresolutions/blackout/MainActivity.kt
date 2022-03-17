package com.smartsoftwaresolutions.blackout

import android.content.Intent
import android.os.Bundle
import android.os.PowerManager
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    //   private Button btn1,btn3;
    private var btn3: Button? = null
    private val powerManager: PowerManager? = null
    private val wakeLock: PowerManager.WakeLock? = null
    private val field = 0x00000020
    var tv: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        btn3 = findViewById(R.id.btn3)
        btn3?.setOnClickListener(View.OnClickListener { // Intent intent=new Intent(MainActivity.this,lock_phone.class);
            val intent = Intent(this@MainActivity, Scean_Bright::class.java)
            // Intent intent=new Intent(MainActivity.this,Audio.class);
            // Intent intent=new Intent(MainActivity.this,SensorActivity.class);
            startActivity(intent)
        })

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