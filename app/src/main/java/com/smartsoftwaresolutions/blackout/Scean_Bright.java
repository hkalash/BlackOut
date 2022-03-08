package com.smartsoftwaresolutions.blackout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.smartsoftwaresolutions.blackout.R;

public class Scean_Bright extends AppCompatActivity {
    //ca-app-pub-8537373371656761~5050991485 this is the test admob id
    private AdView mAdView;
    public int counter;
    Button btn_counter,btn_save,btn_play_Dead,turnOnScreenButton;
    TextView textView,tv_current_count,Black_Time;
    Context context;
    int count_down, current_volume,volume,Black_count_down,S_timeout=0,default_Timeout;

    EditText et;
    SharedPreferences settings;
    public static final String PREFS_NAME = "MyApp_Settings";
    AudioManager mAudioManager;
    CountDownTimer Timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scean__bright);

//facebook
       //Stetho.initializeWithDefaults(this);

        /********* ****************************AdMOb*/
        if(BuildConfig.FLAVOR.equals("Free")){
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        mAdView = findViewById(R.id.adView);


       // AdRequest adRequest = new AdRequest.Builder().build();
      //  AdRequest adRequest = new AdRequest.Builder().addTestDevice("35663D4E3BC0B627A8E5C8571E17588F").build();
      AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.e("onAdLoaded","AdLoaded");

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                Log.e("onAdFailedToLoad",""+errorCode);

            }
        });
        Toast.makeText(Scean_Bright.this,"this is a free version with ads",Toast.LENGTH_LONG).show();
        }
/***************************************************************************************/


        btn_play_Dead=findViewById(R.id.btn_play_Dead);
//        if(BuildConfig.FLAVOR.equals("Free")){
//            btn_play_Dead.setEnabled(false);
//        }else {
//            btn_play_Dead.setEnabled(true);
//        }


        Black_Time=findViewById(R.id.tv2);
        tv_current_count=findViewById(R.id.tv_current_count);
        et=findViewById(R.id.et);
        btn_save=findViewById(R.id.btn_save);
// turn off the save setting in free app
//if (BuildConfig.FLAVOR.equals("Free")){
//    btn_save.setEnabled(false);
//    btn_save.setText(R.string.pro_message);
//}else{
//    btn_save.setEnabled(true);
//    btn_save.setText(R.string.save_setting);
//}

// control the audio
        mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
// get the current volume
        current_volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);


        //sharedpreferance
        //read the screen time out
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        try {
            // save the phone time out
            S_timeout= Settings.System.getInt(getContentResolver(),Settings.System.SCREEN_OFF_TIMEOUT);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("S_timeout", S_timeout);

            editor.commit();
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        default_Timeout=settings.getInt("S_timeout",120000);
// first thing i will read the shared prefrance then i will check the edit text
        count_down= settings.getInt("key_counter", 3);
        String s= String.valueOf(count_down);
        tv_current_count.setText(s+" "+getString(R.string.mint));
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putInt("current_volume", current_volume);
//
//        editor.commit();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et_text= String.valueOf(et.getText());
                if (et_text !=null && !et_text.isEmpty()){
                    // the text is not empty

                    if(BuildConfig.FLAVOR.equals("Free") ){
                        if(Integer.parseInt(et_text)>=1 &&Integer.parseInt(et_text)<=20){
                            //Writing data to SharedPreferences
                            count_down= Integer.parseInt(et_text);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putInt("key_counter", count_down);

                            editor.commit();
                            et.setText("");
                            count_down= settings.getInt("key_counter", 3);
                            String s= String.valueOf(count_down);
                            tv_current_count.setText(s +" "+getString(R.string.mint));
                            //btn_counter.setText("Stop Counter");
                            // btn_counter.setTextColor(getResources().getColor(R.color.Red));
                          //  Go_Black();
                        }else {
                            Toast.makeText(Scean_Bright.this,getString(R.string.please_buy_pro),Toast.LENGTH_LONG).show();
                        }
                        // this is a pro version
                    }else{
                        //Writing data to SharedPreferences
                        count_down= Integer.parseInt(et_text);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("key_counter", count_down);
                        editor.commit();
                        et.setText("");
                        count_down= settings.getInt("key_counter", 3);
                        String s= String.valueOf(count_down);
                        tv_current_count.setText(s +" "+getString(R.string.mint));
                             // read the black time
                        String S_balck_time=String.valueOf(Black_Time.getText());
                        if (S_balck_time!=null && !S_balck_time.isEmpty()){
                            // save the value in sharedpreferance
                            Black_count_down=Integer.parseInt(S_balck_time);
                           // SharedPreferences.Editor editor = settings.edit();
                            editor.putInt("Black_counter", Black_count_down);

                            editor.commit();
                            Black_Time.setText("");

                        }else {
                            // the black time didn't change so read from shared prefrance

                        }
                       // Go_Black();

                    }

                }else {
                    Toast.makeText(Scean_Bright.this,"please enter a value ",Toast.LENGTH_LONG).show();

                }








            }
        });

        context = getApplicationContext();
       // btn_counter =  findViewById(R.id.btn_counter);
        textView=  findViewById(R.id.textView);
        // for test the system
        /**btn_counter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (btn_counter.getText().equals("Start counter")){
                    btn_counter.setText("Stop Counter");
                    btn_counter.setTextColor(getResources().getColor(R.color.Red));
                    Go_Black();
                }else if (btn_counter.getText().equals("Stop Counter")){
                       btn_counter.setText(R.string.counter);

                      Timer.cancel();
                }


            }
        });**/


        btn_play_Dead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BuildConfig.FLAVOR.equals("Free")){
                  Toast.makeText(Scean_Bright.this,R.string.please_buy_pro,Toast.LENGTH_LONG).show();
                }else {
                    boolean settingsCanWrite = false;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        settingsCanWrite = hasWriteSettingsPermission(context);}
//                }else{
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
//                    startActivity(intent);
//                }

                    // If do not have then open the Can modify system settings panel.
                    if(!settingsCanWrite && (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)) {
                        changeWriteSettingsPermission(context);
                    }else {
                        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 500);
                        changeScreenBrightness(context, 1);
                        Intent intent=new Intent(Scean_Bright.this,Black.class);
                        startActivity(intent);
                    }
                }

            }
        });
        // Get turn off screen btn_counter
        final Button test_screen = (Button)findViewById(R.id.test_screen);
        test_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get app context object.
            //    Context context = getApplicationContext();

                // Check whether has the write settings permission or not.
                boolean settingsCanWrite = false;
                // the phone android version where the app is running
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    settingsCanWrite = hasWriteSettingsPermission(context);
                }

                // If do not have then open the Can modify system settings panel.
                if(!settingsCanWrite && (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)) {// if it is a false
                    changeWriteSettingsPermission(context);
                }else {
                    Intent intent=new Intent(Scean_Bright.this,Black.class);
                    startActivity(intent);
                    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 1000);
                    changeScreenBrightness(context, 1);
                    // the millisecound in the future is the time set by you
                    int Set_Time=3000;// every 1000 mili will make a 1 secound
                    new CountDownTimer(Set_Time, 1000){
                        public void onTick(long millisUntilFinished){
                            textView.setText(String.valueOf(counter));
                            counter++;
                        }
                        public  void onFinish(){


                            new CountDownTimer(1000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    counter=0;
                                    textView.setText("FINISH!!");
                                    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, default_Timeout);
                                    changeScreenBrightness(context, 255);
                                }
                            }.start();
                        }
                    }.start();
                    // turn off the screan
                   // changeScreenBrightness(context, 1);
                }
            }
        });

        // Get turn on screen btn_counter
        turnOnScreenButton = findViewById(R.id.turn_on_screen_button);
        turnOnScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           //     Context context = getApplicationContext();

                // Check whether has the write settings permission or not.
                boolean settingsCanWrite = false;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    settingsCanWrite = hasWriteSettingsPermission(context);
                }

                // If do not have then open the Can modify system settings panel.
                if(!settingsCanWrite && (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)) {
                    changeWriteSettingsPermission(context);
                }else {
                    // turn on the screan
                    changeScreenBrightness(context, 255);
                }
            }
        });
    }
    // Check whether this app has android write settings permission.
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean hasWriteSettingsPermission(Context context)
    {
        boolean ret = true;
        // Get the result from below code.
        ret = Settings.System.canWrite(context);
        return ret;
    }

    // Start can modify system settings panel to let user change the write settings permission.
    private void changeWriteSettingsPermission(Context context)
    {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
        context.startActivity(intent);
    }

    // This function only take effect in real physical android device,
    // it can not take effect in android emulator.
    private void changeScreenBrightness(Context context, int screenBrightnessValue)
    {
        // Change the screen brightness change mode to manual.
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        // Apply the screen brightness value to the system, this will change the value in Settings ---> Display ---> Brightness level.
        // It will also change the screen brightness for the device.
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, screenBrightnessValue);

        /*
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = screenBrightnessValue / 255f;
        window.setAttributes(layoutParams);
        */
    }
    private void Go_Black(){
        // the millisecound in the future is the time set by you
        //  int Set_Time=1000;// every 1000 mili will make a 1 secound
        //freeApp
        count_down= settings.getInt("key_counter", 15);
        Black_count_down=settings.getInt("Black_counter",15);
     //   count_down= settings.getInt("key_counter", 3);
     //   Black_count_down=settings.getInt("Black_counter",3);

       // count_down=count_down*1000;
        //free app
        count_down=count_down*1000*60;
        counter=0;
        //   new CountDownTimer(Set_Time, 1000){
    Timer= new CountDownTimer(count_down, 1000){
            public void onTick(long millisUntilFinished){

                textView.setText(String.valueOf(counter));
                counter++;
            }
            public  void onFinish(){
                Intent in=new Intent(Scean_Bright.this,Black.class);
                startActivity(in);
                textView.setText("FINISH!!");
                counter=0;
                volume=0;
                // Check whether has the write settings permission or not.
                boolean settingsCanWrite = false;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    settingsCanWrite = hasWriteSettingsPermission(context);
                }

                // If do not have then open the Can modify system settings panel.
                if(!settingsCanWrite && (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)) {
                    changeWriteSettingsPermission(context);
                }else {
                    changeScreenBrightness(context, 1);

                    // Set media volume level to zero
                    mAudioManager.setStreamVolume(
                            AudioManager.STREAM_MUSIC, // Stream type
                            volume, // Index
                            AudioManager.FLAG_SHOW_UI // Flags
                    );
                    // set the sceen time out to half of a second
                    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 500);
                }


                // send a notification
                NotificationManagerCompat nm = NotificationManagerCompat.from(context);
                NotificationCompat.Builder notif = new NotificationCompat.Builder(context);
                notif.setLights(0xff0000, 1000, 1000);
                notif.setSmallIcon(R.mipmap.ic_launcher);
                notif.setContentTitle("Time is finished");
                nm.notify(5, notif.build());
// then begin a new conunt down to make the screen bright again
           //     new CountDownTimer(5000, 1000) {
                new CountDownTimer(Black_count_down*1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        // Check whether has the write settings permission or not.
                        boolean settingsCanWrite = false;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            settingsCanWrite = hasWriteSettingsPermission(context);
                        }

                        // If do not have then open the Can modify system settings panel.
                        if(!settingsCanWrite && (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)) {
                            changeWriteSettingsPermission(context);
                        }else {
                            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, default_Timeout);
                            changeScreenBrightness(context, 255);}
                        // change the volume to current one
                        // Set media volume level to zero
                        mAudioManager.setStreamVolume(
                                AudioManager.STREAM_MUSIC, // Stream type
                                current_volume, // Index
                                AudioManager.FLAG_SHOW_UI // Flags
                        );
                    }
                }.start();
            }
        }.start();

    }
}
